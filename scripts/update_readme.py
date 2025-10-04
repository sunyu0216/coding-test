import requests
from datetime import datetime

USERNAME = "sunyu0216"  # solved.ac 아이디
README_PATH = "README.md"

def fetch_stats():
    url = f"https://solved.ac/api/v3/user/show?handle=sunyu0216"
    res = requests.get(url)
    data = res.json()
    return {
        "solved": data["solvedCount"],
        "tier": data["tier"],
        "rating": data["rating"],
        "rank": data["rank"]
    }

def update_readme(stats):
    with open(README_PATH, "r", encoding="utf-8") as f:
        lines = f.readlines()

    start = lines.index("<!--START_STATS-->\n") + 1
    end = lines.index("<!--END_STATS-->\n")

    updated_section = f"""
| 날짜 | 총 푼 문제 | 티어 | 레이팅 | 순위 |
|------|-------------|------|---------|------|
| {datetime.now().strftime('%Y-%m-%d')} | {stats['solved']} | {stats['tier']} | {stats['rating']} | {stats['rank']} |
"""

    new_lines = lines[:start] + [updated_section] + lines[end:]

    with open(README_PATH, "w", encoding="utf-8") as f:
        f.writelines(new_lines)

if __name__ == "__main__":
    stats = fetch_stats()
    update_readme(stats)
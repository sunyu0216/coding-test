import requests
from datetime import datetime

USERNAME = "sunyu0216"  # Solved.ac 아이디
README_PATH = "README.md"

# 숫자 티어 → 이름 매핑
TIER_MAPPING = [
    "Unrated", "Bronze V", "Bronze IV", "Bronze III", "Bronze II", "Bronze I",
    "Silver V", "Silver IV", "Silver III", "Silver II", "Silver I",
    "Gold V", "Gold IV", "Gold III", "Gold II", "Gold I",
    "Platinum V", "Platinum IV", "Platinum III", "Platinum II", "Platinum I",
    "Diamond V", "Diamond IV", "Diamond III", "Diamond II", "Diamond I",
    "Ruby V", "Ruby IV", "Ruby III", "Ruby II", "Ruby I"
]

def fetch_stats():
    url = f"https://solved.ac/api/v3/user/show?handle={USERNAME}"
    res = requests.get(url)
    data = res.json()
    return {
        "solved": data["solvedCount"],
        "tier": TIER_MAPPING[data["tier"]],
        "rating": data["rating"],
        "rank": data["rank"]
    }

def update_readme(stats):
    with open(README_PATH, "r", encoding="utf-8") as f:
        content = f.read()

    start_boj = "<!--START_STATS-->"
    end_boj = "<!--END_STATS-->"

    if start_boj in content and end_boj in content:
        parts = content.split(start_boj)
        before = parts[0]
        after = parts[1].split(end_boj)[1]
        new_boj = (
            "| 날짜       | 총 푼 문제 | 티어      | 레이팅 | 순위   |\n"
            "| ---------- | ---------- | --------- | ------ | ------ |\n"
            f"| {datetime.now().strftime('%Y-%m-%d')} | {stats['solved']} | {stats['tier']} | {stats['rating']} | {stats['rank']} |"
        )
        content = before + start_boj + "\n" + new_boj + "\n" + end_boj + after

    with open(README_PATH, "w", encoding="utf-8") as f:
        f.write(content)

if __name__ == "__main__":
    stats = fetch_stats()
    update_readme(stats)
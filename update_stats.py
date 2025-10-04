import requests
from datetime import datetime

USERNAME = "sunyu0216"  # Solved.ac 아이디
README_PATH = "README.md"

def fetch_stats():
    url = f"https://solved.ac/api/v3/user/show?handle={USERNAME}"
    res = requests.get(url)
    data = res.json()
    return {
        "solved": data["solvedCount"],
        "tier": data["tier"]["name"],
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
        new_boj = f"| {datetime.now().strftime('%Y-%m-%d')} | {stats['solved']} | {stats['tier']} | {stats['rating']} | {stats['rank']} |"
        content = before + start_boj + "\n" + new_boj + "\n" + end_boj + after

    with open(README_PATH, "w", encoding="utf-8") as f:
        f.write(content)

if __name__ == "__main__":
    stats = fetch_stats()
    update_readme(stats)
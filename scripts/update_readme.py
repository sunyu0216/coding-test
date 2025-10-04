import requests
from datetime import datetime

USERNAME = "sunyu0216"  # solved.ac 아이디
README_PATH = "README.md"

# 숫자 티어를 이름으로 매핑
TIER_NAMES = [
    "Unrated",        # 0
    "Bronze V",       # 1
    "Bronze IV",      # 2
    "Bronze III",     # 3
    "Bronze II",      # 4
    "Bronze I",       # 5
    "Silver V",       # 6
    "Silver IV",      # 7
    "Silver III",     # 8
    "Silver II",      # 9
    "Silver I",       # 10
    "Gold V",         # 11
    "Gold IV",        # 12
    "Gold III",       # 13
    "Gold II",        # 14
    "Gold I",         # 15
    "Platinum V",     # 16
    "Platinum IV",    # 17
    "Platinum III",   # 18
    "Platinum II",    # 19
    "Platinum I",     # 20
    "Diamond V",      # 21
    "Diamond IV",     # 22
    "Diamond III",    # 23
    "Diamond II",     # 24
    "Diamond I",      # 25
    "Ruby V",         # 26
    "Ruby IV",        # 27
    "Ruby III",       # 28
    "Ruby II",        # 29
    "Ruby I"          # 30
]

def fetch_stats():
    url = f"https://solved.ac/api/v3/user/show?handle={USERNAME}"
    res = requests.get(url)
    data = res.json()
    
    tier_number = data["tier"]
    tier_name = TIER_NAMES[tier_number] if tier_number < len(TIER_NAMES) else "Unrated"

    return {
        "solved": data["solvedCount"],
        "tier": tier_name,
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
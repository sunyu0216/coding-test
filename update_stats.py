import requests
from bs4 import BeautifulSoup
from datetime import datetime

USERNAME_BOJ = "sunyu0216"      # 백준/solved.ac 아이디
USERNAME_PROG = "sunyu0216@naver.com"     # 프로그래머스 닉네임
README_PATH = "README.md"

# 백준 티어 숫자 -> 이름 매핑
TIER_NAMES = [
    "Unrated", "Bronze V", "Bronze IV", "Bronze III", "Bronze II", "Bronze I",
    "Silver V", "Silver IV", "Silver III", "Silver II", "Silver I",
    "Gold V", "Gold IV", "Gold III", "Gold II", "Gold I",
    "Platinum V", "Platinum IV", "Platinum III", "Platinum II", "Platinum I",
    "Diamond V", "Diamond IV", "Diamond III", "Diamond II", "Diamond I",
    "Ruby V", "Ruby IV", "Ruby III", "Ruby II", "Ruby I"
]

# 백준 통계 가져오기
def fetch_boj_stats():
    url = f"https://solved.ac/api/v3/user/show?handle={USERNAME_BOJ}"
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

# 프로그래머스 통계 가져오기
def fetch_programmers_stats():
    url = f"https://school.programmers.co.kr/users/{USERNAME_PROG}/challenges"
    res = requests.get(url)
    soup = BeautifulSoup(res.text, "html.parser")

    solved_elem = soup.select_one(".solved_num")
    solved = solved_elem.text.strip() if solved_elem else "0"

    rank_elem = soup.select_one(".user-rank")
    rank = rank_elem.text.strip() if rank_elem else "Unranked"

    recent = [el.text.strip() for el in soup.select(".challenge_title")[:5]]

    return {
        "solved": solved,
        "rank": rank,
        "recent": recent
    }

# README 업데이트
def update_readme(boj_stats, prog_stats):
    with open(README_PATH, "r", encoding="utf-8") as f:
        content = f.read()

    # 백준 통계
    start_boj = "<!--START_STATS-->"
    end_boj = "<!--END_STATS-->"
    new_boj = f"""
| 날짜 | 총 푼 문제 | 티어 | 레이팅 | 순위 |
|------|-------------|------|---------|------|
| {datetime.now().strftime('%Y-%m-%d')} | {boj_stats['solved']} | {boj_stats['tier']} | {boj_stats['rating']} | {boj_stats['rank']} |
"""
    content = content.split(start_boj)[0] + start_boj + "\n" + new_boj + content.split(end_boj)[1]

    # 프로그래머스 통계
    start_prog = "<!-- PROGRAMMERS:START -->"
    end_prog = "<!-- PROGRAMMERS:END -->"
    recent_list = "\n".join([f"  - {q}" for q in prog_stats['recent']])
    new_prog = f"""
**프로그래머스 현황**
- 푼 문제 수: {prog_stats['solved']}
- 랭크: {prog_stats['rank']}
- 최근 풀이:
{recent_list}
"""
    content = content.split(start_prog)[0] + start_prog + "\n" + new_prog + content.split(end_prog)[1]

    with open(README_PATH, "w", encoding="utf-8") as f:
        f.write(content)

if __name__ == "__main__":
    boj_stats = fetch_boj_stats()
    prog_stats = fetch_programmers_stats()
    update_readme(boj_stats, prog_stats)
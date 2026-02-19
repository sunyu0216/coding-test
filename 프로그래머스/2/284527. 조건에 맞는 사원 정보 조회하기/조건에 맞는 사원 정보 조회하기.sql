-- 코드를 작성해주세요
select sum(a.SCORE) as SCORE, a.EMP_NO, b.EMP_NAME, b.POSITION, b.EMAIL
from HR_GRADE a
join HR_EMPLOYEES b
on a.EMP_NO = b.EMP_NO
GROUP by a.EMP_NO
ORDER BY SUM(SCORE) DESC
LIMIT 1;
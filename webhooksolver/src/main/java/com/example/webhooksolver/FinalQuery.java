package com.example.webhooksolver;

public class FinalQuery {

    public static String getQuery() {
        return """
WITH emp_sums AS (
  SELECT emp.EMP_ID, emp.DEPARTMENT AS DEPARTMENT_ID, COALESCE(SUM(p.AMOUNT),0) AS SALARY
  FROM EMPLOYEE emp
  LEFT JOIN PAYMENTS p ON emp.EMP_ID = p.EMP_ID AND DAY(p.PAYMENT_TIME) <> 1
  GROUP BY emp.EMP_ID, emp.DEPARTMENT
),
ranked AS (
  SELECT es.*, ROW_NUMBER() OVER (PARTITION BY es.DEPARTMENT_ID ORDER BY es.SALARY DESC, es.EMP_ID) AS rn
  FROM emp_sums es
)
SELECT d.DEPARTMENT_NAME, r.SALARY, CONCAT(e.FIRST_NAME,' ',e.LAST_NAME) AS EMPLOYEE_NAME,
       TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE
FROM ranked r
JOIN DEPARTMENT d ON d.DEPARTMENT_ID = r.DEPARTMENT_ID
JOIN EMPLOYEE e ON e.EMP_ID = r.EMP_ID
WHERE r.rn = 1;
""";
    }
}

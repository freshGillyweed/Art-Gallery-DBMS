package ui;

import javax.swing.*;


/*
SELECT *
FROM table1
WHERE NOT EXISTS
    (   (SELECT table2.atr1
         FROM table2 )
     EXCEPT
         (SELECT table3.atr2
         FROM table3
         WHERE table3.atr3 = table1.atr4)
     )


SELECT sname
FROM Student S
WHERE NOT EXISTS
 ((SELECT C.name
 FROM Class C)
 EXCEPT
 (SELECT E.cname
 FROM Enrolled E
 WHERE E.snum=S.snum))
 */

public class Division extends JFrame{

    Division() {

    }
}

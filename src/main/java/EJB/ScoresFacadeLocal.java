/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Scores;

/**
 *
 * @author jsali
 */
@Local
public interface ScoresFacadeLocal {

    void create(Scores scores);

    void edit(Scores scores);

    void remove(Scores scores);

    Scores find(Object id);

    List<Scores> findAll();

    List<Scores> findRange(int[] range);

    int count();
    
}

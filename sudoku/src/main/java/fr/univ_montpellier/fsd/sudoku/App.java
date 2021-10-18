//Equipe : Filali Yasmine - Chemaou Doha
package fr.univ_montpellier.fsd.sudoku;

public class App
{
    public static void main( String[] args )
    {
        long start = System.currentTimeMillis();
        new fr.univ_montpellier.fsd.sudoku.ppc.Sudoku().solve();
        long finish = System.currentTimeMillis();
        float timeElapsed = (float) (finish - start)/1000;

        System.out.println("Temps d'exécution du solver choco : " + timeElapsed + "s");
        start = System.currentTimeMillis();

        new fr.univ_montpellier.fsd.sudoku.imp.Sudoku(4).findSolution();

        finish = System.currentTimeMillis();
        timeElapsed = (float) (finish - start)/1000;

        System.out.println("Temps d'execution de notre programme : "+timeElapsed+"s");

    }
    /*Pour n = 4 temps d'execution de notre solution = 0.001s --- contre 0.096s pour le modèle PPC.
    Pour n = 9 temps d'execution de notre solution = 0.003s --- contre 0.099ss pour le modèle PPC.
    Pour n = 16 temps d'execution de notre solution = 0.006s --- contre 0.105s pour le modèle PPC.
    Pour n = 25 temps d'execution de notre solution = 0.01s --- contre 0.098s pour le modèle PPC.
    Pour n = 36 temps d'execution de notre solution = 0.013s --- contre 0.102s pour le modèle PPC.
    Pour n = 49 temps d'execution de notre solution = 0.021s --- contre 0.11s pour le modèle PPC.


    En comparant le temps d'execution de notre programme avec  le modèle PPC on trouve
    que notre implémentation génere plus rapidement une solution correcte.
     */
}

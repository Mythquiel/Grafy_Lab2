package graphs;

import java.io.IOException;
import java.util.*;

public class GraphProcessor {

    boolean hasCycle = false, zad2 = true, isCircle = false;
    int cycleNo;
    FileCreator fileCreator;

    /*
    Zadanie 1
    Napisz program, który wczyta z pliku opis grafu G a następnie sprawdzi, czy graf G jest grafem regularnym
    i jeżeli tak, to jakiego stopnia.
    czyli posiadające wszystkie wierzchołki tego samego stopnia, czyli z każdego wierzchołka grafu regularnego wychodzi dokładanie taka sama ilość krawędzi
    */
    void checkIfRegular(Integer[][] graphTable, int vertexNo, int edgeNo) {
        ArrayList<Integer> level = new ArrayList<>();
        boolean isRegular = true;

        for (int inc = 0; inc < vertexNo; inc++) {
            level.add(0);
        }

        for (int row = 0; row < edgeNo; row++) {
            for (int column = 0; column < 2; column++) {
                level.set(graphTable[row][column] - 1, level.get(graphTable[row][column] - 1) + 1);
            }
        }

        for (int lista = 0; lista < level.size(); lista++) {

            if (lista != level.size() - 1) {
                if (level.get(lista) != level.get(lista + 1)) {
                    isRegular = false;
                }
            }
        }

        if (isRegular == true) {
            System.out.println("Graf jest regularny.");
            System.out.println("Stopien grafu to " + level.get(0));
        } else {
            System.out.println("Graf nie jest regularny.");
        }
    }

    /*
    Zadanie 2
    Napisz program, który wczyta z pliku opis grafu G a następnie sprawdzi, czy graf G jest grafem cyklicznym
    i jeżeli tak, to na jego podstawie utworzy nowy graf W będący kołem. Wyświetl na ekranie listę krawędzi grafu W
    oraz zapisz graf W do pliku w.txt (struktura pliku wynikowego znajduje się na końcu dokumentu).
    */
    void checkIfCyclic(ArrayList<Integer>[] graphTable, int vertexNo, int edgeNo, ArrayList<Integer>[] cycles, Integer[][] graphTab, String task) {

        hasCycle = false;
        int[] color = new int[100];
        int[] par = new int[100];
        int[] mark = new int[100];
        cycleNo = 0;


        dfs(1, 0, color, mark, par, graphTable);

        for (int i = 1; i < edgeNo; i++) {
            if (mark[i] != 0)
                cycles[mark[i]].add(i);
        }

        if (task == "zad2") {
            if (cycleNo > 0) {
                hasCycle = true;
                if (zad2) {
                    fileCreator = new FileCreator(createCircle(cycles[cycleNo], vertexNo, graphTab), "w");
                    try {
                        fileCreator.createFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Graf jest grafem cyklicznym. Wynik zadania 2 został zapisany do pliku w.txt");

                    zad2 = false;
                } else {
                    isCircle = true;
                }
            } else {
                hasCycle = false;
                if (zad2) {
                    System.out.println("Graf nie jest grafem cyklicznym.");
                    zad2 = false;
                } else {
                    isCircle = false;
                }
            }
        } else {
            if (cycleNo > 0) {
                hasCycle = true;
                isCircle = true;
            } else {
                hasCycle = false;
                isCircle = false;
            }
        }
    }

    void dfs(int u, int p, int[] color, int[] mark, int[] par, ArrayList<Integer>[] graphArray) {
        // already (completely) visited vertex.
        if (color[u] == 2) {
            return;
        }

        if (color[u] == 1) {
            cycleNo++;
            int cur = p;
            mark[cur] = cycleNo;

            while (cur != u) {
                cur = par[cur];
                mark[cur] = cycleNo;
            }
            return;
        }
        par[u] = p;
        color[u] = 1;
        for (int v : graphArray[u]) {

            // if it has not been visited previously
            if (v == par[u]) {
                continue;
            }
            dfs(v, u, color, mark, par, graphArray);
        }
        color[u] = 2;
    }

    Integer[][] createCircle(ArrayList<Integer> cycles, int vertexNo, Integer[][] graphTable) {
        int additionalVertex = vertexNo + 1;
        Integer[][] cycleGraph = new Integer[100][2];
        for (int i = 0; i < 100; i++) {
            cycleGraph[i][0] = 0;
            cycleGraph[i][1] = 0;
        }
        int row = 0;
        while (row < graphTable.length && graphTable[row][0] != 0 && graphTable[row][1] != 0) {
            cycleGraph[row][0] = graphTable[row][0];
            cycleGraph[row][1] = graphTable[row][1];
            row++;
        }
        int incr = 0;
        for (int inc = row; inc < row + cycles.size(); inc++) {
            cycleGraph[inc][0] = cycles.get(incr) + 1;
            cycleGraph[inc][1] = additionalVertex;
            //System.out.println((cycles.get(incr) + 1) + "-" + additionalVertex);
            incr++;
        }
        return cycleGraph;
    }

    /*
    Zadanie 3
    Napisz program, który wczyta z pliku opis grafu G a następnie sprawdzi, czy graf G jest grafem typu koło
    i jeżeli tak, to na jego podstawie utworzy nowy graf cykliczny C. Wyświetl na ekranie listę krawędzi grafu C oraz
    zapisz graf C do pliku c.txt (struktura pliku wynikowego znajduje się na końcu dokumentu).
    */
    void checkIfCircle(int vertexNo, Integer[][] graphTable, int edgeNo) {
        boolean isCircle = false;

        ArrayList<Integer>[] circleArray = new ArrayList[100];
        ArrayList<Integer>[] cycles = new ArrayList[100];
        Integer[][] circleTable = new Integer[100][2];
        for (int i = 0; i < 100; i++) {
            circleArray[i] = new ArrayList<>();
            cycles[i] = new ArrayList<>();
            circleTable[i][0] = 0;
            circleTable[i][1] = 0;
        }

        int inc = 0;
        if (hasCycle) {
            for (int i = 0; i < graphTable.length; i++) {
                if (graphTable[i][0] == vertexNo || graphTable[i][1] == vertexNo) {
                } else {
                    circleTable[inc][0] = graphTable[i][0];
                    circleTable[inc][1] = graphTable[i][1];
                    inc++;
                    //System.out.println((circleTable[inc][0]) + "--" + (circleTable[inc][1]));
                }
            }
            int row = 0;
            while (circleTable[row][0] != 0 && circleTable[row][1] != 0) {
                circleArray[circleTable[row][0]].add(circleTable[row][1]);
                circleArray[circleTable[row][1]].add(circleTable[row][0]);
                row++;
            }
            checkIfCyclic(circleArray, vertexNo, edgeNo, cycles, graphTable, "zad3");
            if (hasCycle == true) {
                fileCreator = new FileCreator(circleTable, "c");
                try {
                    fileCreator.createFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Graf jest typu koło. Wynik zadania 3 został zapisany do pliku c.txt");
            } else {
                System.out.println("Graf nie jest kołem.");
            }


        }
    }

    /*
    Zadanie 4 (bonusowe)
    Napisz program, który wczyta z pliku opis grafu G a następnie pokoloruje wierzchołki grafu w taki sposób,
    aby żadnej krawędzi nie były przypisane te same kolory. Pokolorowany graf zapisz do pliku k.txt (struktura pliku
    wynikowego znajduje się na końcu dokumentu).
    */
    void colorGraph(int vertexNo, ArrayList<Integer>[] adj, Integer[][] graphTable) throws IOException {

        int result[] = new int[vertexNo];
        boolean available[] = new boolean[vertexNo];
        Arrays.fill(available, true);

        for (int inc = 1; inc < vertexNo; inc++) {
            Iterator<Integer> iterator = adj[inc].iterator();
            while (iterator.hasNext()) {
                int i = iterator.next();
                if (result[i] != -1) {
                    available[result[i]] = false;
                }
            }

            int color;
            for (color = 0; color < vertexNo; color++) {
                if (available[color]) {
                    break;
                }
            }

            result[inc] = color;
            Arrays.fill(available, true);

        }
        System.out.println("Pokolorowany graf zostal zapisany do pliku k.txt");
        fileCreator = new FileCreator(graphTable, "k", result);
        fileCreator.createFile();


    }

    /*
    Zadanie 5
    Napisz program, który wczyta z pliku opis grafu G a następnie sprawdzi, czy graf G jest grafem eulerowskim,
    półeulerowskim lub nieeulerowskim
    */
    void checkIfEulerian(int vertexNo, ArrayList<Integer>[] graphArray) {
        int result = isEulerian(graphArray, vertexNo);
        switch (result) {
            case 0:
                System.out.println("Graf nie jest eulerowski");
                break;
            case 1:
                System.out.println("Graf jest poleulerowski");
                break;
            case 2:
                System.out.println("Graf jest eulerowski");
                break;
            default:
                System.out.println("Nie da sie stwierdzic, czy graf jest eulerowski");
        }


    }

    private int isEulerian(ArrayList<Integer>[] graphArray, int vertexNo) {
        if (isConnected(vertexNo, graphArray) == false) {
            return 0;
        }
        int odd = 0;
        for (int i = 0; i < vertexNo; i++) {
            if (graphArray[i].size() % 2 != 0) {
                odd++;
            }
        }

        if (odd > 2) {
            return 0;
        }
        return (odd == 2) ? 1 : 2;
    }

    private boolean isConnected(int vertexNo, ArrayList<Integer>[] graphArray) {
        boolean visited[] = new boolean[vertexNo];
        int i;
        for (i = 0; i < vertexNo; i++)
            visited[i] = false;

        for (i = 0; i < vertexNo; i++)
            if (graphArray[i].size() != 0)
                break;

        if (i == vertexNo)
            return true;

        DFSUtil(i, visited, graphArray);

        for (i = 0; i < vertexNo; i++)
            if (visited[i] == false && graphArray[i].size() > 0)
                return false;

        return true;
    }

    private void DFSUtil(int vertexNo, boolean[] visited, ArrayList<Integer>[] graphArray) {
        {
            visited[vertexNo] = true;

            Iterator<Integer> i = graphArray[vertexNo].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n])
                    DFSUtil(n, visited, graphArray);
            }
        }
    }
}
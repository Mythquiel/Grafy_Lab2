package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileProcessor {

    String fileName;
    BufferedReader openedFile;
    Integer[][] graphTable;
    ArrayList<Integer>[] cycles;
    ArrayList<Integer>[] graphArray;
    int vertexNo, edgeNo;

    FileProcessor(String fileName) {
        this.fileName = fileName;
    }

    BufferedReader openFile() {
        try {
            openedFile = new BufferedReader(
                    new FileReader(fileName));

        } catch (FileNotFoundException e) {
            System.out.println("Nie ma takiego pliku");
            System.exit(1);
        }

        return openedFile;

    }

    void processFile(BufferedReader openedFile) throws IOException {
        int lineNo = 0;
        graphArray = new ArrayList[100];
        cycles = new ArrayList[100];
        String line;
        boolean isFirst = true;
        Integer[][] temp2 = new Integer[100][2];
        ArrayList<Integer> vertex = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            graphArray[i] = new ArrayList<>();
            cycles[i] = new ArrayList<>();
        }

        while ((line = openedFile.readLine()) != null) {
            if (isFirst) {
                isFirst = false;
                lineNo++;
                continue;
            }
            if (!line.startsWith("//") && !line.startsWith("}")) {
                edgeNo++;
                String[] temp = line.split(" -- ");
                temp2[lineNo - 1][0] = Integer.parseInt(temp[0].substring(4, 5));
                temp2[lineNo - 1][1] = Integer.parseInt(temp[1].substring(0, 1));
                graphArray[(Integer.parseInt(temp[0].substring(4, 5)))-1].add((Integer.parseInt(temp[1].substring(0, 1)))-1);
                graphArray[(Integer.parseInt(temp[1].substring(0, 1)))-1].add((Integer.parseInt(temp[0].substring(4, 5)))-1);
                if (!vertex.contains(temp2[lineNo - 1][1])) {
                    vertex.add(temp2[lineNo - 1][1]);
                }
                if (!vertex.contains(temp2[lineNo - 1][0])) {
                    vertex.add(temp2[lineNo - 1][0]);
                }
            }
            lineNo++;
        }
        vertexNo = vertex.size();
        graphTable = new Integer[edgeNo][edgeNo];
        for (int edgeI = 0; edgeI < edgeNo; edgeI++) {
            graphTable[edgeI][0] = Integer.parseInt(String.valueOf(temp2[edgeI][0]));
            graphTable[edgeI][1] = Integer.parseInt(String.valueOf(temp2[edgeI][1]));
        }
    }

    Integer[][] getGraphTable() {
        return graphTable;
    }

    int getEdgeNo() {
        return edgeNo;
    }

    int getVertexNo() {
        return vertexNo;
    }

    void closeFile() {
        try {
            openedFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

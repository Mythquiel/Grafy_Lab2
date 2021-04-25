package graphs;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String filename = null;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        FileProcessor fileProcessor = null;

            System.out.print("Podaj nazwe pliku: ");
            filename = userInput.readLine();
            userInput.close();
            //filename="grafnc.txt";
            fileProcessor = new FileProcessor(filename);
            fileProcessor.openFile();
            fileProcessor.processFile(fileProcessor.openedFile);

        GraphProcessor graphProcessor= new GraphProcessor();
//Zad1
       // graphProcessor.checkIfRegular(fileProcessor.getGraphTable(), fileProcessor.getVertexNo(), fileProcessor.getEdgeNo());
//Zad2
        // graphProcessor.checkIfCyclic(fileProcessor.graphArray,fileProcessor.getVertexNo(),fileProcessor.getEdgeNo(),fileProcessor.cycles, fileProcessor.getGraphTable(), "zad2");
//Zad3
       //  graphProcessor.checkIfCyclic(fileProcessor.graphArray,fileProcessor.getVertexNo(),fileProcessor.getEdgeNo(),fileProcessor.cycles, fileProcessor.getGraphTable(), "zad3");
       //  graphProcessor.checkIfCircle(fileProcessor.getVertexNo(),fileProcessor.getGraphTable(), fileProcessor.getEdgeNo());
//Zad4
       // graphProcessor.colorGraph(fileProcessor.getVertexNo(),fileProcessor.graphArray, fileProcessor.getGraphTable());
//Zad5
         graphProcessor.checkIfEulerian(fileProcessor.getVertexNo(),fileProcessor.graphArray);

        fileProcessor.closeFile();


    }

}

package graphs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreator {

    private Integer[][] graphTable;
    private String fileName;
    private int[] colors;

    public FileCreator(Integer[][] graphTable, String fileName) {
        this.graphTable = graphTable;
        this.fileName = fileName;
    }

    public FileCreator(Integer[][] graphTable, String fileName, int[] result){
        this.graphTable=graphTable;
        this.fileName=fileName;
        this.colors=result;
    }

    void createFile() throws IOException {
        FileWriter file = null;
        BufferedWriter writer = null;
        try {
            file = new FileWriter(fileName+".txt");
            writer = new BufferedWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.write("Graph "+fileName.toUpperCase()+" {");
        writer.newLine();
        if(colors==null) {
            fillFileNoColor(writer);
        } else {
            fillFileColor(writer, colors);
        }
        writer.close();
        file.close();


    }

    private void fillFileNoColor(BufferedWriter writer) throws IOException {
        int graphInc=0;
        while (graphInc<graphTable.length && graphTable[graphInc][0] != 0 && graphTable[graphInc][1] != 0 ) {
            writer.write("    "+graphTable[graphInc][0]+" -- "+graphTable[graphInc][1]+";");
            writer.newLine();
            graphInc++;
        }
        writer.write("}");

    }

    private void fillFileColor(BufferedWriter writer, int[] colors) throws IOException {
        int graphInc=0;
        while (graphInc<graphTable.length && graphTable[graphInc][0] != 0 && graphTable[graphInc][1] != 0 ) {
            writer.write("    w"+graphTable[graphInc][0]+"k"+colors[graphTable[graphInc][0]-1]+" -- w"+graphTable[graphInc][1]+"k"+colors[graphTable[graphInc][1]-1]+";");
            writer.newLine();
            graphInc++;
        }
        writer.write("}");
    }
}

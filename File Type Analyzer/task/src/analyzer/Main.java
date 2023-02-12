package analyzer;
public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Wrong arguments");
        } else {
            FileProcessing.checkFiles(args);
        }
    }
}

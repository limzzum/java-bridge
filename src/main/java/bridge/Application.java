package bridge;

import java.util.ArrayList;
import java.util.List;

public class Application {

    static InputView inputView = new InputView();
    static OutputView outputView = new OutputView();
    static BridgeGame bridgeGame= new BridgeGame();
    static List<String> visited;

    public static void main(String[] args) {
        try {
            init();
            gameStart();
            printGameResult();
        }catch (IllegalArgumentException illegalArgumentException){
            System.out.println(illegalArgumentException.getMessage());
        }

    }

    private static void init() {
        String input = inputView.readBridgeSize();
        bridgeGame.init(input);
    }

    private static void gameStart() {
        while (true){
            boolean keep = move();
            if(!keep && !retry()){
                break;
            }
            if(bridgeGame.isGameCompleted()){
                break;
            }
        }
    }

    private static boolean move() {
        String direction = inputView.readMoving();
        visited = bridgeGame.move(direction);
        printUpBridge();
        printDownBridge();

        return bridgeGame.isRightDirection(visited.size() - 1, direction);
    }

    static private void printDownBridge() {
        List<String> downString = new ArrayList<>();
        for(int i=0; i<visited.size(); i++){
            boolean rightDirection = bridgeGame.isRightDirection(i, visited.get(i));
            String result = bridgeGame.getResult(i,"D", rightDirection);
            downString.add(result);
        }
        outputView.printMap(downString);
    }

    static private void printUpBridge() {
        List<String> upString = new ArrayList<>();
        for(int i=0; i<visited.size(); i++){
            boolean rightDirection = bridgeGame.isRightDirection(i, visited.get(i));
            String result = bridgeGame.getResult(i,"U", rightDirection);
            upString.add(result);
        }
        outputView.printMap(upString);
    }

    public String getResult(int i, String direction, boolean rightDirection) {
        String result = "O";
        if(!rightDirection){
            result = "X";
        }
        if(!visited.get(i).equals(direction)){
            result =" ";
        }
        return result;
    }

    static private boolean retry(){
        String select = inputView.readGameCommand();
        return bridgeGame.retry(select);
    }

    static private void printGameResult(){
        outputView.printEnding();
        printUpBridge();
        printDownBridge();
        String result = bridgeGame.getGameResult();
        int tryNum = bridgeGame.getTryNum();
        outputView.printResult(result,tryNum);
    }



}


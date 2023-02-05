package org.example;

public class PieceTour {

    private static final int size=10;
    private static int[][] chessBoard = new int[size][size];

    private static final int xInitPos=0;
    private static final int yInitPos=0;

    private static final int xTour[] = { 3, 2, 0, -2, -3, -2, 0, 2 };
    private static final int yTour[] = { 0, 2, 3, 2, 0, -2, -3, -2 };

    public static void main(String[] args) {

        int path=1;
        chessBoard[xInitPos][yInitPos]=path;

        if(pieceMovement(xInitPos,yInitPos,path+1)){
            printChessBoard();
        }else{
            System.out.println("Solution doesn't exist");
        }

    }

    private static boolean pieceMovement(int xCurrPos, int yCurrPos,int path) {
        if(path==size*size){
            return true;
        }
        for(int i=0;i<xTour.length;i++){
            int xNextPos=xCurrPos+xTour[i];
            int yNextPos=yCurrPos+yTour[i];

            if(validateCoordinates(xNextPos,yNextPos)){
                chessBoard[xNextPos][yNextPos]=path;
                if(pieceMovement(xNextPos,yNextPos,path+1)){
                    return true;
                }else{
                    chessBoard[xNextPos][yNextPos] = 0;
                }
            }
        }
        return false;
    }

    private static boolean validateCoordinates(int xNextPos, int yNextPos) {
        return (xNextPos>=0
                && xNextPos<size
                && yNextPos>=0
                && yNextPos<size
                && chessBoard[xNextPos][yNextPos]==0);
    }


    private static void printChessBoard(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                System.out.print(chessBoard[i][j]+"\t");
            }
            System.out.println();
        }
    }
}

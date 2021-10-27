package chess;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Models.*;
/**
 * @author Rohan Joshi
 * @author Nicholas Cheniara
 */

public class Chess {

    /**
     * 
     * The main method of the chess game containing
     * the main game loop as well as logic to handle special scenarios 
     * such as castling, enpassant, promotion
     * 
     * 
     * @param args  Command line arguments stored in an array of Strings
     * @throws IOException  Throws an exception because of bufferedreader
     */

    public static void main(String [] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Board gameboard = new Board();
        String line = null;
        gameboard.drawBoard();
        boolean drawoffered = false;
        HashMap<String,Piece> piecemap = new HashMap<>();
        piecemap.put("N", new Knight(false, false, false));
        piecemap.put("R", new Rook(false, false, false));
        piecemap.put("Q", new Queen(false, false, false));
        piecemap.put("B", new Bishop(false, false, false));
        ArrayList<String> moves = new ArrayList<>();
        int movecount = -1;

        while(true){
            if(gameboard.whiteTurn){
                System.out.println();
                System.out.print("White's move: ");
            }else{
                System.out.println();
                System.out.print("Black's move: ");
            }
            line = br.readLine();
            movecount++;
            moves.add(line);
            if (line.length() == 0) {
                break;
            }
            String[] inputs = line.split(" ");
            if(inputs[0].equals("resign")){
                if(gameboard.whiteTurn){
                    System.out.println("Black wins");
                    break;
                }
                else{
                    System.out.println("White wins");
                    break;
                }

            }
            if(inputs.length==3){
                if(inputs[2].equals("draw?")){
                    drawoffered=true;
                    gameboard.whiteTurn = !gameboard.whiteTurn;
                    continue;
                }

                if(inputs[2].length()==1){
                    Position s = Board.ctop(inputs[0].charAt(0),inputs[0].charAt(1));
                    Position f = Board.ctop(inputs[1].charAt(0),inputs[1].charAt(1));
                    Piece c = gameboard.getXY(s.x, s.y);
                    
                    if(c.isLegalMove(s, f, gameboard)){
                        gameboard.movePiece(s, f);
                        Piece temp = piecemap.get(inputs[2]);
                        gameboard.setXY(f.x, f.y, temp);
                        if(c.isWhite){
                            temp.isWhite=true;
                        }
                    }
                    else{
                        System.out.println("Illegal move, try again.");
                        continue;
                    }
                    if(c.isWhite != gameboard.whiteTurn){
                        System.out.println("Illegal move, try again.");
                        continue;
                    }
                    if(gameboard.checkBlack || !gameboard.whiteTurn){
                        if(Board.simulateMove(s, f, gameboard).equals("black")){
                            System.out.println("Illegal move, try again.");
                            continue;
                        }
                    }
        
                    if(gameboard.checkWhite || gameboard.whiteTurn){
                        if(Board.simulateMove(s, f, gameboard).equals("white")){
                            System.out.println("Illegal move, try again.");
                            continue;
                        }
                    }
                    if(Board.detectblackcheck(gameboard)){
                        System.out.println("Black is in check!");
                        gameboard.checkBlack = true;
                    }
                    else{
                        gameboard.checkBlack = false;
                    }
                    if(Board.detectwhitecheck(gameboard)){
                        System.out.println("White is in check!");
                        gameboard.checkWhite = true;
                    }
                    else{
                        gameboard.checkWhite = false;
                    }
                    if(Board.detectCheckmatewhite(gameboard)){
                        System.out.println("White is in checkmate!");
                        System.out.println("White wins!");
                        break;
                    }
                    if(Board.detectCheckmateblack(gameboard)){
                        System.out.println("Black is in checkmate!");
                        System.out.println("Black wins!");
                        break;
                    }
        
        
                    gameboard.whiteTurn = !gameboard.whiteTurn;
                    gameboard.drawBoard();
                    continue;


                }
            }
            if(drawoffered){
                if(inputs.length>1){
                    System.out.println("You must accept the draw");
                }
                if(inputs[0].equals("draw")){
                    break;
                }
            }
            Position start = Board.ctop(inputs[0].charAt(0),inputs[0].charAt(1));
            Position finish = Board.ctop(inputs[1].charAt(0),inputs[1].charAt(1));
            Position rando = new Position(start.x, start.y+1);
            Position rando2 = new Position(start.x, start.y-1);
            Piece cur = gameboard.getXY(start.x, start.y);
            Boolean checkerofthings = false;
            Boolean otherchecker = false;
            
            if(movecount>=1){
                String[] splitmove = moves.get(movecount-1).split(" ");
                Position prevstart = Board.ctop(splitmove[0].charAt(0),splitmove[0].charAt(1));
                Position prevfinish = Board.ctop(splitmove[1].charAt(0),splitmove[1].charAt(1));
                Piece usedpiece = gameboard.getXY(prevfinish.x, prevfinish.y);
                if((usedpiece instanceof Pawn)&&Math.abs(prevfinish.x-prevstart.x)==2){
                    gameboard.enpassantactive = true;
                    gameboard.enpassantpos = prevfinish;
                }
                else{
                    gameboard.enpassantactive = false;
                    gameboard.enpassantpos = null;
                }
            }   
            if(gameboard.enpassantactive){
                if(gameboard.whiteTurn){
                    if(gameboard.enpassantpos.x==start.x){
                        if((gameboard.enpassantpos.y==start.y+1)|| (gameboard.enpassantpos.y==start.y-1)){
                            if(finish.x==gameboard.enpassantpos.x-1){
                                gameboard.setXY(gameboard.enpassantpos.x, gameboard.enpassantpos.y, null);
                                gameboard.boardmat[finish.x][finish.y] = new Pawn(cur.hasMoved,false,true);
                                otherchecker=true;
                            }
                        }
                    }
                }
                else{
                    if(gameboard.enpassantpos.x==start.x){
                        if((gameboard.enpassantpos.y==start.y+1)|| (gameboard.enpassantpos.y==start.y-1)){
                            if(finish.x==gameboard.enpassantpos.x+1){
                                gameboard.setXY(gameboard.enpassantpos.x, gameboard.enpassantpos.y, null);
                                gameboard.boardmat[finish.x][finish.y] = new Pawn(cur.hasMoved,false,false);
                                otherchecker=true;
                            }
                        }
                    }

                }
            }


            if(((start.x==1 && finish.x==0) || (start.x==6 && finish.x==7))&& (cur instanceof Pawn)){
                if(cur.isLegalMove(start, finish, gameboard)){
                    gameboard.movePiece(start, finish);
                    gameboard.setXY(finish.x,finish.y, new Queen(false, false, cur.isWhite));
                    checkerofthings = true;
                }

            }

            if(cur==null){
                System.out.println("Illegal move, try again.");
                continue;
            }
            Piece fin = gameboard.getXY(finish.x, finish.y);
            if(cur instanceof King && fin instanceof Rook){
                if(cur.isWhite == fin.isWhite){
                    if(!cur.hasMoved && !fin.hasMoved){
                        //right castle
                        if(fin.isLegalMove(finish, rando, gameboard)){
                            if(finish.y > start.y){
                                gameboard.CastledBlack=true;
                                for(int k = start.y;k<=finish.y;k++){
                                    Position tpos = new Position(start.x, k);
                                    if(!Board.simulateMove(start, tpos, gameboard).equals("none")){
                                        gameboard.CastledBlack=false;
                                    }
                                }
                                if(gameboard.CastledBlack){
                                    Piece tempcur = cur;
                                    Piece tempfin = fin;
                                    gameboard.setXY(finish.x, finish.y, tempcur);
                                    gameboard.setXY(start.x, start.y, tempfin);
                                    gameboard.drawBoard();
                                    gameboard.whiteTurn = !gameboard.whiteTurn;
                                    continue;
                                }
                                else{
                                    System.out.println("Illegal move, try again.");
                                    continue;
                                }

                            }

                        }
                    //left castle
                    if(fin.isLegalMove(finish, rando2, gameboard)){
                        if(finish.y < start.y){
                            gameboard.CastledBlack=true;
                            for(int k = start.y;k>=finish.y;k--){
                                Position tpos = new Position(start.x, k);
                                if(!Board.simulateMove(start, tpos, gameboard).equals("none")){
                                    gameboard.CastledBlack=false;
                                }
                            }
                            if(gameboard.CastledBlack){
                                Piece tempcur = cur;
                                Piece tempfin = fin;
                                gameboard.setXY(finish.x, finish.y, tempcur);
                                gameboard.setXY(start.x, start.y, tempfin);
                                gameboard.drawBoard();
                                gameboard.whiteTurn = !gameboard.whiteTurn;
                                continue;
                            }
                            else{
                                System.out.println("Illegal move, try again.");
                                continue;
                            }

                        }
                    }
                    }
                }
            }

            if(cur.isWhite != gameboard.whiteTurn){
                System.out.println("Wrong Color Piece!");
                continue;
            }
            if(gameboard.checkBlack || !gameboard.whiteTurn){
                if(Board.simulateMove(start, finish, gameboard).equals("black")){
                    System.out.println("Illegal move, try again.");
                    continue;
                }
            }

            if(gameboard.checkWhite || gameboard.whiteTurn){
                if(Board.simulateMove(start, finish, gameboard).equals("white")){
                    System.out.println("Illegal move, try again.");
                    continue;
                }
            }
            if(!checkerofthings || !otherchecker){
                if(cur.isLegalMove(start, finish, gameboard)){
                    cur.hasMoved = true;
                    gameboard.movePiece(start, finish);
                }
                else{
                    System.out.println("Illegal move, try again.");
                    continue;
                }
            
            }


            if(Board.detectblackcheck(gameboard)){
                System.out.println("check");
                gameboard.checkBlack = true;
            }
            else{
                gameboard.checkBlack = false;
            }
            if(Board.detectwhitecheck(gameboard)){
                System.out.println("check");
                gameboard.checkWhite = true;
            }
            else{
                gameboard.checkWhite = false;
            }
            if(Board.detectCheckmatewhite(gameboard)){
                System.out.println("checkmate");
                System.out.println("Black wins!");
                break;
            }
            if(Board.detectCheckmateblack(gameboard)){
                System.out.println("checkmate");
                System.out.println("White wins!");
                break;
            }


            gameboard.whiteTurn = !gameboard.whiteTurn;
            gameboard.drawBoard();
        }
        br.close();
        return;
    }
}

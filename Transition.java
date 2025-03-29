public class Transition {
    
    private String fromState;
    private Character readSymbol;
    private Character writeSymbol;
    private Character move;
    private String toState;


    public Transition(String from, char readSymbol, char writeSymbol, char move, String toState)
    {
        this.fromState = from;
        this.readSymbol = readSymbol;
        this.writeSymbol = writeSymbol;
        this.move = move;
        this.toState = toState;
    }

    public String getFromState() {return fromState; }
    public char getReadSymbol() {return readSymbol; }
    public char getWriteSymbol() { return writeSymbol; }
    public char getMove() { return move; }
    public String getToState() { return toState; } 

}

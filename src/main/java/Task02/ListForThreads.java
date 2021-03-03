package Task02;

import java.util.ArrayList;
import java.util.List;

public class ListForThreads {
    private List<Integer> list;
    private Integer lastWriterIndex;
    private Integer lastSummarizerIndex;
    private Integer lastRooterIndex;
    private Integer sum;
    private Integer sumOfSquares;

    public ListForThreads() {
        this.list = new ArrayList<>();
        this.lastWriterIndex = -1;
        this.lastSummarizerIndex = -1;
        this.lastRooterIndex = -1;
        this.sum = 0;
        this.sumOfSquares = 0;
    }

    public List<Integer> getList() {
        return list;
    }

    public Integer getLastWriterIndex() {
        return lastWriterIndex;
    }

    public void setLastWriterIndex(Integer lastWriterIndex) {
        this.lastWriterIndex = lastWriterIndex;
    }

    public Integer getLastSummarizerIndex() {
        return lastSummarizerIndex;
    }

    public void setLastSummarizerIndex(Integer lastSummarizerIndex) {
        this.lastSummarizerIndex = lastSummarizerIndex;
    }

    public Integer getLastRooterIndex() {
        return lastRooterIndex;
    }

    public void setLastRooterIndex(Integer lastRooterIndex) {
        this.lastRooterIndex = lastRooterIndex;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getSumOfSquares() {
        return sumOfSquares;
    }

    public void setSumOfSquares(Integer sumOfSquares) {
        this.sumOfSquares = sumOfSquares;
    }
}

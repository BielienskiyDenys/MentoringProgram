package task_02;

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
        this.lastWriterIndex = Task02Constants.LAST_WRITER_INDEX_DEFAULT;
        this.lastSummarizerIndex = Task02Constants.LAST_SUMMARIZER_INDEX_DEFAULT;
        this.lastRooterIndex = Task02Constants.LAST_ROOTER_INDEX_DEFAULT;
        this.sum = Task02Constants.SUM_DEFAULT;
        this.sumOfSquares = Task02Constants.SUM_OF_SQUARES_DEFAULT;
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

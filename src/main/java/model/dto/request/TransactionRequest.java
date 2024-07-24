package model.dto.request;

import java.util.List;

public class TransactionRequest {
    private String customerId;
    private String staffId;
    private List<String> bookIds;

    public TransactionRequest(String customerId, String staffId, List<String> bookIds) {
        this.customerId = customerId;
        this.staffId = staffId;
        this.bookIds = bookIds;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }
}

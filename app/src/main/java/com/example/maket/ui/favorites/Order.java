package com.example.maket.ui.favorites;

public class Order {

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_CANCELLED = 2;
    public static final int STATUS_COMPLETED = 3;
    public static final int STATUS_EXPIRED = 4;

    private int orderId;
    private String productName;
    private String partnerName;
    private String pickupUntil;
    private int status; // 1 активен, 2 отменён, 3 получен, 4 истёк

    public Order(int orderId, String productName, String partnerName, String pickupUntil, int status) {
        this.orderId = orderId;
        this.productName = productName;
        this.partnerName = partnerName;
        this.pickupUntil = pickupUntil;
        this.status = status;
    }

    public int getOrderId() { return orderId; }
    public String getProductName() { return productName; }
    public String getPartnerName() { return partnerName; }
    public String getPickupUntil() { return pickupUntil; }
    public int getStatus() { return status; }

    public String getStatusText() {
        switch (status) {
            case STATUS_ACTIVE: return "Активен";
            case STATUS_CANCELLED: return "Отменён";
            case STATUS_COMPLETED: return "Получен";
            case STATUS_EXPIRED: return "Истёк";
            default: return "Неизвестно";
        }
    }
}

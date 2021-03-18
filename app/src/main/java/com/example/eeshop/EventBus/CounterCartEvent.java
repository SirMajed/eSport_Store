package com.example.eeshop.EventBus;

public class CounterCartEvent {
    // هذا الكلاس عشان يسوي كاونت لمن نضيف ايتم في السله
    private boolean success;

    public CounterCartEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

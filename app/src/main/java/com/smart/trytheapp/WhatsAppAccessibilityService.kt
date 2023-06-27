package com.smart.trytheapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class WhatsAppAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName?.toString() == "com.smart.sukoon" && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Toast.makeText(this, "sukoon Launched testing.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInterrupt() {
        // Do nothing
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        info.packageNames = arrayOf("com.smart.sukoon")
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        serviceInfo = info
    }
}

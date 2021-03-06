package com.example.entitys.real.util;

import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Trigger;

public class JobDispatcherUtils {
    public static JobTrigger periodicTrigger(int frequency, int tolerance) {
        return Trigger.executionWindow(frequency - tolerance, frequency);
    }
}
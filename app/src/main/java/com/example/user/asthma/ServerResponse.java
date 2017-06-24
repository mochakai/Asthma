package com.example.user.asthma;

import android.content.Context;

/**
 * interface to handle server response due to caller
 */

interface ServerResponse {
    void onServerResponse(String result, Context caller);
}

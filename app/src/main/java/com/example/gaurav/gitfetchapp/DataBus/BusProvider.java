package com.example.gaurav.gitfetchapp.DataBus;

import com.squareup.otto.Bus;

/**
 * Created by GAURAV on 16-08-2016.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
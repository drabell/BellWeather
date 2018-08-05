/*
 Project        :   TILTMETER
 Class          :   SessionSettings
 Description    :   Persist Session Settings
 Author         :   Alexander Bell
 Copyright      :   2017 Alexander Bell
 Company        :   Infosoft International Inc
 Release        :   12/12/2017
 Modified       :   12/20/2017
 ----------------------------------------------------------------------------------------------
 This module is provided on 'AS IS' basis
 This module is copyrighted. You can use/modify it keeping the copyright notice intact.
*/
package biz.infosoft.bellweather.util;

public final class SessionSettings {
    // display location search option
    public volatile static boolean allowSearch = false;

    // connection time out
    public static final int TIMEOUT_MS = 10000;
}

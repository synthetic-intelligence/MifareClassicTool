/*
 * Copyright 2020 Gerhard Klostermeier
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package de.syss.MifareClassicTool.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.syss.MifareClassicTool.Common;
import de.syss.MifareClassicTool.R;

public class UidLogTool extends BasicActivity {

    TextView mUidLog;

    // TODO: doc.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid_log_tool);
        mUidLog = findViewById(R.id.textViewUidLogToolUids);
        updateUidLog();
    }

    // TODO: doc.
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateUidLog();
    }

    /**
     * Add the menu with the share/clear functions to the Activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.uid_log_tool_functions, menu);
        return true;
    }

    /**
     * Handle the selected function from the menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.menuUidLogToolShare:
                shareUidLog();
                return true;
            case R.id.menuUidLogToolClear:
                clearUidLog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: doc.
    private void updateUidLog() {
        File log = new File(this.getFilesDir(),
                Common.HOME_DIR + File.separator + Common.UID_LOG_FILE);
        String[] logEntries = Common.readFileLineByLine(log, false, this);
        if (logEntries != null) {
            // Reverse order (newest top).
            ArrayList<String> tempEntries =
                    new ArrayList<String>(Arrays.asList(logEntries));
            Collections.reverse(tempEntries);
            mUidLog.setText(TextUtils.join(
                    System.getProperty("line.separator"), tempEntries));
        } else {
            // No log yet.
            mUidLog.setText(R.string.text_no_uid_logs);
        }
    }

    // TODO: doc.
    private void clearUidLog() {
        File log = new File(this.getFilesDir(),
                Common.HOME_DIR + File.separator + Common.UID_LOG_FILE);
        if (log.exists()){
            log.delete();
        }
        updateUidLog();
    }

    // TODO: doc.
    private void shareUidLog() {
        File log = new File(this.getFilesDir(),
                Common.HOME_DIR + File.separator + Common.UID_LOG_FILE);
        if (log.exists()) {
            Common.shareTextFile(this, log);
        }
    }
}
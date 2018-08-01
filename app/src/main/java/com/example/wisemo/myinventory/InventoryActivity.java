package com.example.wisemo.myinventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wisemo.myinventory.data.ItemDbHelper;
import com.example.wisemo.myinventory.data.ItemContract.ItemEntry;

public class InventoryActivity extends AppCompatActivity {

    // Create an instance from the ItemDbHelper
    private ItemDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // Instantiate the mDbHelper
        mDbHelper = new ItemDbHelper(this);
    }
    /* @Override onStart method */
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Helper method to display information in the onscreen TextView about the state of
     * the products items database.
     */
    private void displayDatabaseInfo() {

        // The projection that specifies which columns from the database to be viewed
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_CATEGORY_TYPE,
                ItemEntry.COLUMN_ITEM_PRODUCT_NAME,
                ItemEntry.COLUMN_ITEM_DESCRIPTION,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_AVAILABILITY,
                ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemEntry.COLUMN_ITEM_SUPPLIER_NAME,
                ItemEntry.COLUMN_ITEM_SUPPLIER_PHONE_NUMBER };

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link ItemEntry.CONTENT_URI} to access the item data.
        Cursor cursor = getContentResolver().query(
                ItemEntry.CONTENT_URI,    // The content URI
                projection,               // The columns that return for each row
                null,            // Selection criteria
                null,         // SelectionArgs criteria
                null);           // The sort order for returned rows

        // Assign the textView which will display the cursor query.
        TextView displayView = findViewById(R.id.item_text_view);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The items table contains <number of rows in Cursor> items.
            // _id - type - product_name - description - availability - price - quantity - supplier_name - phone_number
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The items table contains " + cursor.getCount() + " items.\n\n");
            displayView.append(ItemEntry._ID + " - " +
                    ItemEntry.COLUMN_ITEM_CATEGORY_TYPE + " - " +
                    ItemEntry.COLUMN_ITEM_PRODUCT_NAME + " - " +
                    ItemEntry.COLUMN_ITEM_DESCRIPTION + " - " +
                    ItemEntry.COLUMN_ITEM_PRICE + " - " +
                    ItemEntry.COLUMN_ITEM_AVAILABILITY + " - " +
                    ItemEntry.COLUMN_ITEM_QUANTITY + " - " +
                    ItemEntry.COLUMN_ITEM_SUPPLIER_NAME + " - " +
                    ItemEntry.COLUMN_ITEM_SUPPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ItemEntry._ID);
            int itemTypeColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_CATEGORY_TYPE);
            int itemNameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRODUCT_NAME);
            int itemDescriptionColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_DESCRIPTION);
            int itemPriceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
            int itemAvailabilityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_AVAILABILITY);
            int itemQuantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
            int itemSupplierNameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_SUPPLIER_NAME);
            int itemSupplierPhoneColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_SUPPLIER_PHONE_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                int currentItemType = cursor.getInt(itemTypeColumnIndex);
                String currentItemName = cursor.getString(itemNameColumnIndex);
                String currentItemDescription = cursor.getString(itemDescriptionColumnIndex);
                int currentItemPrice = cursor.getInt(itemPriceColumnIndex);
                int currentItemAvailability = cursor.getInt(itemAvailabilityColumnIndex);
                int currentItemQuantity = cursor.getInt(itemQuantityColumnIndex);
                String currentItemSupplierName = cursor.getString(itemSupplierNameColumnIndex);
                long currentItemSupplierPhone = cursor.getLong(itemSupplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentItemType + " - " +
                        currentItemName + " - " +
                        currentItemDescription + " - " +
                        currentItemPrice + " - " +
                        currentItemAvailability + " - " +
                        currentItemQuantity + " - " +
                        currentItemSupplierName + " - " +
                        currentItemSupplierPhone));
            }
        } finally {
            // cursor close
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // Create a factory object as a LayoutInflater from the MainActivity.class
                LayoutInflater factory = LayoutInflater.from(InventoryActivity.this);
                // Create a View object to the factory LayoutInflater and use the .inflate method to the about_layout.xml, null for the root view.
                final View aboutDialogView = factory.inflate(R.layout.about_layout, null);
                // Create an AlertDialog object and pass this as the .Builder context params then create it by .create().
                final AlertDialog aboutDialog = new AlertDialog.Builder(InventoryActivity.this).create();
                // Sets the AlertDialog view to the aboutDialogView View object.
                aboutDialog.setView(aboutDialogView);
                // Assign a setOnClickListener (new View) to the Linkedin button to open my in URL with an ACTION_VIEW intent, and leave the user to choose a browser.
                aboutDialogView.findViewById(R.id.in_connect_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String moInUrl = "https://www.linkedin.com/in/wisemo/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(moInUrl));
                        startActivity(i);
                        aboutDialog.dismiss();
                    }
                });
                // Assign a setOnClickListener (new View) to dismiss the about view.
                aboutDialogView.findViewById(R.id.about_back_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aboutDialog.dismiss();
                    }
                });
                // Make the About view background transparent.
                aboutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                aboutDialog.show();
                break;
            default:
                break;
        }
        return true;
    }
}
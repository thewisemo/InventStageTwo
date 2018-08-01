package com.example.wisemo.myinventory;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wisemo.myinventory.data.ItemContract.ItemEntry;

public class EditorActivity extends AppCompatActivity {

    /* Spinner field to enter the product item's category Type */
    private Spinner mTypeSpinner;

    /* EditText field to enter the product item's Name */
    private EditText mProductNameEditText;

    /* EditText field to enter the product item's Description */
    private EditText mDescriptionEditText;

    /* EditText field to enter the product item's Price */
    private EditText mPriceEditText;

    /* Spinner field to enter the product item's Availability */
    private Spinner mInStockSpinner;

    /* EditText field to enter the product item's Quantity */
    private EditText mQuantityEditText;

    /* Spinner field to enter the product item's Supplier Name */
    private Spinner mSupplierNameSpinner;

    /* EditText field to enter the product item's Supplier Phone Number */
    private EditText mSupplierPhoneNumberEditText;

    /**
     * Type of the item. The possible valid values are in the ItemContract.java file:
     * {@link ItemEntry#TYPE_CASUAL}, OR
     * {@link ItemEntry#TYPE_FORMAL}, OR
     * {@link ItemEntry#TYPE_COTTON_SOCKS}, OR
     * {@link ItemEntry#TYPE_CARE_PRODUCTS}, OR
     * {@link ItemEntry#TYPE_BELTS_AND_WALLETS}.
     */
    private int mType = ItemEntry.TYPE_CASUAL;

    /**
     * Availability of the item. The possible valid values are in the ItemContract.java file:
     * {@link ItemEntry#ITEM_IN_STOCK}, OR
     * {@link ItemEntry#ITEM_OUT_STOCK}.
     */
    private int mInStock = ItemEntry.ITEM_IN_STOCK;

    /**
     * Suppliers of the item. The possible valid values are in the ItemContract.java file:
     * * {@link ItemEntry#SUPPLIER_MAIN}, OR
     * {@link ItemEntry#SUPPLIER_NUMBER_ONE}, OR
     * {@link ItemEntry#SUPPLIER_NUMBER_TWO}, OR
     * {@link ItemEntry#SUPPLIER_NUMBER_THREE}.
     */
    private int mSupplierName = ItemEntry.SUPPLIER_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mTypeSpinner = findViewById(R.id.spinner_category_type);
        mProductNameEditText = findViewById(R.id.edit_item_name);
        mDescriptionEditText = findViewById(R.id.edit_item_description);
        mPriceEditText = findViewById(R.id.edit_item_price);
        mInStockSpinner = findViewById(R.id.spinner_stock_availability);
        mQuantityEditText = findViewById(R.id.edit_item_quantity);
        mSupplierNameSpinner = findViewById(R.id.spinner_item_supplier_name);
        mSupplierPhoneNumberEditText = findViewById(R.id.edit_item_phone_number);

        setupTypeSpinner();
        setupAvailabilitySpinner();
        setupSupplierSpinner();
    }

    /* Setup the dropdown spinner that allows the user to select the item Type. */
    private void setupTypeSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_type_options, android.R.layout.simple_spinner_item);
        // Specify dropdown layout style - simple list view with 1 item per line
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mTypeSpinner.setAdapter(typeSpinnerAdapter);
        // Set the integer mSelected to the constant values
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_belts_and_wallets))) {
                        mType = ItemEntry.TYPE_BELTS_AND_WALLETS; // Belts And Wallets
                    } else if (selection.equals(getString(R.string.type_care_products))) {
                        mType = ItemEntry.TYPE_CARE_PRODUCTS;  // Care Products
                    } else if (selection.equals(getString(R.string.type_cotton_socks))) {
                        mType = ItemEntry.TYPE_COTTON_SOCKS;  // Cotton Socks
                    } else if (selection.equals(getString(R.string.type_formal))) {
                        mType = ItemEntry.TYPE_FORMAL;  // Formal
                    } else {
                        mType = ItemEntry.TYPE_CASUAL;  // Casual
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = ItemEntry.TYPE_CASUAL;
            }
        });
    }

    /* Setup the dropdown spinner that allows the user to select the item Availability. */
    private void setupAvailabilitySpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter availabilitySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_availability_options, android.R.layout.simple_spinner_item);
        // Specify dropdown layout style - simple list view with 1 item per line
        availabilitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mInStockSpinner.setAdapter(availabilitySpinnerAdapter);
        // Set the integer mSelected to the constant values
        mInStockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.item_in_stock))) {
                        mInStock = ItemEntry.ITEM_IN_STOCK; // In Stock
                    } else {
                        mInStock = ItemEntry.ITEM_OUT_STOCK; // Out Of Stock
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mInStock = ItemEntry.ITEM_OUT_STOCK;
            }
        });
    }

    /* Setup the dropdown spinner that allows the user to select the item Supplier Name. */
    private void setupSupplierSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter supplierSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_name_options, android.R.layout.simple_spinner_item);
        // Specify dropdown layout style - simple list view with 1 item per line
        supplierSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        mSupplierNameSpinner.setAdapter(supplierSpinnerAdapter);
        // Set the integer mSelected to the constant values
        mSupplierNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_number_one))) {
                        mSupplierName = ItemEntry.SUPPLIER_NUMBER_ONE; // Number One
                        mSupplierPhoneNumberEditText.setText(R.string.phone_supplier_number_one);
                    } else if (selection.equals(getString(R.string.supplier_number_two))) {
                        mSupplierName = ItemEntry.SUPPLIER_NUMBER_TWO;  // Number Two
                        mSupplierPhoneNumberEditText.setText(R.string.phone_supplier_number_two);
                    } else if (selection.equals(getString(R.string.supplier_number_three))) {
                        mSupplierName = ItemEntry.SUPPLIER_NUMBER_THREE;  // Number Three
                        mSupplierPhoneNumberEditText.setText(R.string.phone_supplier_number_three);
                    } else {
                        mSupplierName = ItemEntry.SUPPLIER_MAIN; // Main Supplier
                        mSupplierPhoneNumberEditText.setText(R.string.phone_supplier_main);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mSupplierName = ItemEntry.SUPPLIER_MAIN;
            }
        });
    }

    /* Get user input from editor and save new item into database. */
    private void insertItem() {
        // Read from input fields
        String itemNameString = mProductNameEditText.getText().toString().trim();
        String itemDescriptionString = mDescriptionEditText.getText().toString().trim();

        String itemPriceString = mPriceEditText.getText().toString().trim();
        int price;
        try {
            price = Integer.parseInt(itemPriceString);
        } catch (NumberFormatException e) {
            // handle the exception
            price = 0;
        }

        String itemQuantityString = mQuantityEditText.getText().toString().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(itemQuantityString);
        } catch (NumberFormatException e) {
            // handle the exception
            quantity = 0;
        }

        // TODO show the supplier phone number once the user select the supplier name from its spinner
        String itemSupplierPhoneString = mSupplierPhoneNumberEditText.getText().toString().trim();

        // Create a ContentValues object "values" where column names are the keys,
        // and product item attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_CATEGORY_TYPE, mType);
        values.put(ItemEntry.COLUMN_ITEM_PRODUCT_NAME, itemNameString);
        values.put(ItemEntry.COLUMN_ITEM_DESCRIPTION, itemDescriptionString);
        values.put(ItemEntry.COLUMN_ITEM_PRICE, price);
        values.put(ItemEntry.COLUMN_ITEM_AVAILABILITY, mInStock);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quantity);
        values.put(ItemEntry.COLUMN_ITEM_SUPPLIER_NAME, mSupplierName);
        values.put(ItemEntry.COLUMN_ITEM_SUPPLIER_PHONE_NUMBER, itemSupplierPhoneString);

        // Insert a new item into the provider, returning the content URI for the new item.
        Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this,itemNameString + " " + getString(R.string.editor_insert_item_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save product item to database
                insertItem();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (InventoryActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
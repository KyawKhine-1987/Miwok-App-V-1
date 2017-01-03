package net.freelance.android.miwok.testing;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Kyaw Khine on 12/02/2016.
 * This class is no need for miwok app.
 */
public class NumbersClickListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Open the list of numbers.", Toast.LENGTH_SHORT).show();
    }
}

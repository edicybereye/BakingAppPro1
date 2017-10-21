package android.edikurniawan.me.bakingapplication.utility;

import android.text.Spannable;

/**
 * Created by Edi Kurniawan on 21/11/2016.
 */
public interface OnFoundListener {
    void onFound(Spannable spannable, int start, int end, int openTag, int closeTag);
}

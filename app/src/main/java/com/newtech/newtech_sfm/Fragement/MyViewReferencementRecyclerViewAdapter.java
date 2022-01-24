package com.newtech.newtech_sfm.Fragement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.newtech.newtech_sfm.Configuration.VisibiliteLigneViewModel;
import com.newtech.newtech_sfm.Fragement.ViewReferencementFragment.OnListFragmentInteractionListener;
import com.newtech.newtech_sfm.Fragement.dummy.DummyContent.DummyItem;
import com.newtech.newtech_sfm.Metier.Article;
import com.newtech.newtech_sfm.Metier.VisibiliteLigne;
import com.newtech.newtech_sfm.Metier_Manager.ArticleManager;
import com.newtech.newtech_sfm.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyViewReferencementRecyclerViewAdapter extends RecyclerView.Adapter<MyViewReferencementRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MyViewReferencementRecyclerViewAdapter.class.getSimpleName();

    private final ArrayList<VisibiliteLigne> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;
    private final Fragment fragment;
    ArticleManager articleManager;
    private final VisibiliteLigneViewModel visibiliteLigneViewModel;

    public MyViewReferencementRecyclerViewAdapter(ArrayList<VisibiliteLigne> visibiliteLignes, OnListFragmentInteractionListener listener, Context context, Fragment fragment) {
        mValues = visibiliteLignes;
        mListener = listener;
        this.context =context;
        this.fragment = fragment;
        Log.d(TAG, "onCreateView: activity "+fragment.getActivity().toString());
        visibiliteLigneViewModel = ViewModelProviders.of(fragment.getActivity()).get(VisibiliteLigneViewModel.class);
        articleManager = new ArticleManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_viewreferencement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Article article = articleManager.get(mValues.get(position).getARTICLE_CODE());
        holder.article_code.setText(mValues.get(position).getARTICLE_CODE());
        holder.article_designation.setText(article.getARTICLE_DESIGNATION1());

        String base64Image = "";

        if(article.getIMAGE().toString().contains(",")){
            base64Image = String.valueOf(article.getIMAGE()).split(",")[1];
        }else{
            base64Image=article.getIMAGE();
        }
        if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){
            if(getImageId(context, article.getARTICLE_CODE().toLowerCase())>0){
                holder.article_image.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));
            }else{
                holder.article_image.setImageResource(getImageId(context,"bouteille_inconnu2"));
            }
        }else{
            byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            holder.article_image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Integer position = (Integer) holder.image_existe.getTag();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues.get(position));
                }
            }
        });

        holder.image_visibile.setTag(position);
        if(mValues.get(position).getVISIBILITE() == 1)
        {
            Log.d(TAG, "onBindViewHolder: "+position);
            Log.d(TAG, "onBindViewHolder: item "+mValues.get(position).toString());
            setUnlocked(holder.image_visibile);
            holder.image_visibile.setImageResource(getImageId(context,"image_visible"));

        }else{
            holder.image_visibile.setImageResource(getImageId(context,"image_not_visible"));
            setLocked(holder.image_visibile);
        }

        holder.image_existe.setTag(position);
        if(mValues.get(position).getREFERENCEMENT() == 1)
        {
            setUnlocked(holder.image_existe);
            if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){
                if(getImageId(context, article.getARTICLE_CODE().toLowerCase())>0){
                    holder.image_existe.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));
                }else{
                    holder.image_existe.setImageResource(getImageId(context,"bouteille_inconnu2"));
                }
            }else{
                byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
                holder.image_existe.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
            }

        }else{
            //holder.image_existe.setImageBitmap(null);
            if(base64Image.length()<10 || base64Image.equals("") || base64Image==null){
                if(getImageId(context, article.getARTICLE_CODE().toLowerCase())>0){
                    holder.image_existe.setImageResource(getImageId(context, article.getARTICLE_CODE().toLowerCase()));
                }else{
                    holder.image_existe.setImageResource(getImageId(context,"bouteille_inconnu2"));
                }
            }else{
                byte[] decodedString= Base64.decode(base64Image,Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
                holder.image_existe.setImageBitmap(Bitmap.createScaledBitmap(decodedByte,decodedByte.getWidth() , decodedByte.getHeight(), false));
            }

            setLocked(holder.image_existe);
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView article_code = null;
        TextView article_designation = null;
        ImageView article_image = null;
        ImageView image_existe = null;
        ImageView image_visibile = null;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            article_code = (TextView) view.findViewById(R.id.article_code);
            article_designation = (TextView) view.findViewById(R.id.article_designation);
            article_image = (ImageView) view.findViewById(R.id.image_article);
            image_existe = (ImageView) view.findViewById(R.id.image_existe);
            image_visibile = (ImageView) view.findViewById(R.id.image_visible);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public static void  setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);   // 128 = 0.5
    }

    public static void  setUnlocked(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }





}

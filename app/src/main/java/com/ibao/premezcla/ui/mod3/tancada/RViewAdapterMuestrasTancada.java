package com.ibao.premezcla.ui.mod3.tancada;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.premezcla.R;
import com.ibao.premezcla.models.Muestra;

import java.util.List;


public class RViewAdapterMuestrasTancada
        extends RecyclerView.Adapter<RViewAdapterMuestrasTancada.ViewHolder>
        implements View.OnClickListener{

    private View.OnClickListener onClickListener;

    private Context ctx;
    private List<Muestra> muestraList;

    final private String TAG = RViewAdapterMuestrasTancada.class.getSimpleName();

    public RViewAdapterMuestrasTancada(Context ctx, List<Muestra> muestraList) {
        this.ctx = ctx;
        this.muestraList = muestraList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mod3_act_tancada_item_muestra,viewGroup,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClicListener(View.OnClickListener listener){
        this.onClickListener=listener;

    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int pos) {
        Muestra item = muestraList.get(pos);
        holder.tm_tViewDateStart.setText(""+item.getStartMuestra());
        holder.tm_tViewLines.setText(""+item.getLines());
        holder.tm_tViewDistance.setText(""+getFloatFormateado(item.getDistancy()));
        holder.tm_tViewDuracion.setText(""+item.getDuration());
    }


    public float getFloatFormateado(double n){
        return ((int)(n*10))/10.0f;
    }


    @Override
    public int getItemCount() {
        return muestraList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tm_tViewDateStart;
        TextView tm_tViewLines;
        TextView tm_tViewDistance;
        TextView tm_tViewDuracion;
        ImageView tm_iViewFace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tm_tViewDateStart = itemView.findViewById(R.id.tm_tViewDateStart);
            tm_tViewLines = itemView.findViewById(R.id.tm_tViewLines);
            tm_tViewDistance = itemView.findViewById(R.id.tm_tViewDistance);
            tm_tViewDuracion = itemView.findViewById(R.id.tm_tViewDuracion);
            tm_iViewFace = itemView.findViewById(R.id.tm_iViewFace);
        }
    }




}



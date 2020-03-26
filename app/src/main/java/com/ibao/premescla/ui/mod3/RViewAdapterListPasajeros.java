package com.ibao.premescla.ui.mod3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.premescla.R;
import com.ibao.premescla.models.Tancada;

import java.util.List;


public class RViewAdapterListPasajeros
        extends RecyclerView.Adapter<RViewAdapterListPasajeros.ViewHolder>
        implements View.OnClickListener{

    private View.OnClickListener onClickListener;

    private Context ctx;
    private List<Tancada> tancadaList;

    final private String TAG = RViewAdapterListPasajeros.class.getSimpleName();

    public RViewAdapterListPasajeros(Context ctx, List<Tancada> tancadaList) {
        this.ctx = ctx;
        this.tancadaList = tancadaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tancada_item,viewGroup,false);
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
        Tancada tancada = tancadaList.get(pos);

        //fin labels
       // holder.tViewName.setText(""+tancada.getName());
       // holder.tViewDNI.setText(""+tancada.getDni());
       // holder.tViewObservacion.setText("*"+tancada.getObservacion());
       // holder.tViewFecha.setText(""+tancada.gethSubida());

    }

    public float getFloatFormateado(float n){
        return ((int)(n*10))/10.0f;
    }


    @Override
    public int getItemCount() {
        return tancadaList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fmain_item_nOrden;
        TextView fmain_item_Fundo;
        TextView fmain_item_Empresa;
        TextView fmain_item_nTankAll;
        TextView fmain_item_nTankComplete;
        TextView fmain_item_dateTime;
        LinearLayout fmain_item_finish;
        LinearLayout fmain_item_onprocess;

        FloatingActionButton tareo_item_fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fmain_item_onprocess = itemView.findViewById(R.id.fmain_item_onprocess);
            fmain_item_finish = itemView.findViewById(R.id.fmain_item_finish);
            fmain_item_nOrden = itemView.findViewById(R.id.fmain_item_nOrden);
            fmain_item_Fundo = itemView.findViewById(R.id.fmain_item_Fundo);
            fmain_item_Empresa = itemView.findViewById(R.id.fmain_item_Empresa);
            fmain_item_nTankComplete = itemView.findViewById(R.id.fmain_item_nTankComplete);
            fmain_item_nTankAll = itemView.findViewById(R.id.fmain_item_nTankAll);
            fmain_item_dateTime = itemView.findViewById(R.id.fmain_item_dateTime);

        }
    }




}



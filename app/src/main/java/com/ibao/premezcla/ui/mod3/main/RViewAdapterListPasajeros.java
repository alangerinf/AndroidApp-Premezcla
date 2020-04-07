package com.ibao.premezcla.ui.mod3.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.premezcla.R;
import com.ibao.premezcla.models.Tancada;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mod3_act_main_item_tancada,viewGroup,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClicListener(View.OnClickListener listener){
        this.onClickListener=listener;

    }

    public Tancada getItem(int pos){
        return tancadaList.get(pos);
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
        Tancada tancada = getItem(pos);

        holder.ti_tViewOrdenCode.setText(""+tancada.getCodeOrden());
        holder.ti_tViewStatus.setText(""+tancada.getEstadoTancada());
        holder.ti_tViewLoteCode.setText(""+tancada.getCodeLote());
        holder.ti_tViewNroTancada.setText(""+tancada.getNroTancada());
        holder.ti_tViewMojamiento.setText(""+tancada.getMojamiento());
        holder.ti_tViewMuestras.setText(""+tancada.getMuestras().size());

        String timeStart = tancada.getFechaInicioAplicacion();
        String timeEnd = tancada.getFechaFinAplicacion();

        if(!timeStart.isEmpty()){
            holder.ti_tViewTimeStart.setText(""+timeStart);
        }
        if(!timeEnd.isEmpty()){
            holder.ti_tViewTimeEnd.setText(""+timeEnd);
        }

        String conductor = tancada.getNombreConductor();
        String tractor = tancada.getNombreTractor();
        if(!conductor.isEmpty()){
            holder.ti_tViewConductor.setText(""+conductor);
        }
        if(!tractor.isEmpty()){
            holder.ti_tViewTractor.setText(""+tractor);
        }

    }

    @Override
    public int getItemCount() {
        return tancadaList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView ti_tViewNroTancada;
        TextView ti_tViewMojamiento;
        TextView ti_tViewLoteCode;
        TextView ti_tViewStatus;
        TextView ti_tViewMuestras;
        TextView ti_tViewTimeStart;
        TextView ti_tViewTimeEnd;
        TextView ti_tViewConductor;
        TextView ti_tViewTractor;
        TextView ti_tViewOrdenCode;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ti_tViewNroTancada = itemView.findViewById(R.id.ti_tViewNroTancada);
            ti_tViewMojamiento = itemView.findViewById(R.id.ti_tViewMojamiento);
            ti_tViewLoteCode = itemView.findViewById(R.id.ti_tViewLoteCode);
            ti_tViewStatus = itemView.findViewById(R.id.ti_tViewStatus);
            ti_tViewMuestras = itemView.findViewById(R.id.ti_tViewMuestras);
            ti_tViewTimeStart = itemView.findViewById(R.id.ti_tViewTimeStart);
            ti_tViewTimeEnd = itemView.findViewById(R.id.ti_tViewTimeEnd);
            ti_tViewConductor = itemView.findViewById(R.id.ti_tViewConductor);
            ti_tViewTractor = itemView.findViewById(R.id.ti_tViewTractor);
            ti_tViewOrdenCode = itemView.findViewById(R.id.ti_tViewOrdenCode);

        }
    }




}



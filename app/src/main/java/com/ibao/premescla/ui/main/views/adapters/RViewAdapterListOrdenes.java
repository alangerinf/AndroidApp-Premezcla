package com.ibao.premescla.ui.main.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.premescla.R;
import com.ibao.premescla.models.Orden;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RViewAdapterListOrdenes
        extends RecyclerView.Adapter<RViewAdapterListOrdenes.ViewHolder>
        implements View.OnClickListener{

    private List<Orden> tareoDetalleVOList;

    private Context ctx;

    private View.OnClickListener onClickListener;

    private String TAG = RViewAdapterListOrdenes.class.getSimpleName();

    public RViewAdapterListOrdenes(Context ctx, List<Orden> ordenList) {

        this.tareoDetalleVOList = new ArrayList<>();
                this.tareoDetalleVOList.addAll(ordenList);
        this.ctx = ctx;

    }

    public Orden getOrden (int index){
        return tareoDetalleVOList.get(index);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_item,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orden  item = getOrden(position);
        holder.fmain_item_Fundo.setText(""+item.getCultivoName()+"\n"+item.getVariedadName());
        holder.fmain_item_Empresa.setText(""+item.getFundoName());
        holder.fmain_item_dateTime.setText(""+item.getAplicacionDate());
        holder.fmain_item_nOrden.setText(""+item.getOrdenCode());
        holder.fmain_item_nTankAll.setText(""+item.getTancadasProgramadas());
        holder.fmain_item_nTankComplete.setText(""+item.getCantComplete());
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

    @Override
    public int getItemCount() {
        return tareoDetalleVOList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fmain_item_nOrden;
        TextView fmain_item_Fundo;
        TextView fmain_item_Empresa;
        TextView fmain_item_nTankAll;
        TextView fmain_item_nTankComplete;
        TextView fmain_item_dateTime;

        FloatingActionButton tareo_item_fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fmain_item_nOrden = itemView.findViewById(R.id.fmain_item_nOrden);
            fmain_item_Fundo = itemView.findViewById(R.id.fmain_item_Fundo);
            fmain_item_Empresa = itemView.findViewById(R.id.fmain_item_Empresa);
            fmain_item_nTankComplete = itemView.findViewById(R.id.fmain_item_nTankComplete);
            fmain_item_nTankAll = itemView.findViewById(R.id.fmain_item_nTankAll);
            fmain_item_dateTime = itemView.findViewById(R.id.fmain_item_dateTime);

        }
    }
}

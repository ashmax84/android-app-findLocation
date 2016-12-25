package com.arnon.ofir.mapstest3.more;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.arnon.ofir.mapstest3.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
* Device list adapter.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class DeviceListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private List<BluetoothDevice> mData;
	private OnPairButtonClickListener mListener;
	private FirebaseDatabase database;
	private String _permission;

	public DeviceListAdapter(Context context) {

		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<BluetoothDevice> data, String permission) {
		mData = data;
		_permission = permission;
	}

	public void setListener(OnPairButtonClickListener listener) {
		mListener = listener;
	}

	public int getCount() {
		return (mData == null) ? 0 : mData.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView			=  mInflater.inflate(R.layout.list_item_device, null);

			holder 				= new ViewHolder();

			holder.nameTv		= (TextView) convertView.findViewById(R.id.tv_name);
			holder.addressTv 	= (TextView) convertView.findViewById(R.id.tv_address);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final BluetoothDevice device	= mData.get(position);

		holder.nameTv.setText(device.getName());
		holder.addressTv.setText(device.getAddress());
		if(_permission.equals("admin")) {
			holder.pairBtn		= (Button) convertView.findViewById(R.id.btn_pair);
			holder.pairBtn.setText((device.getBondState() == BluetoothDevice.BOND_BONDED) ? "Unpair" : "Pair");
			holder.pairBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mListener != null) {
						mListener.onPairButtonClick(position);
					}
				}
			});
		}else {
			//TODO check if in the database and than show in map
		}



		return convertView;
	}

	static class ViewHolder {
		TextView nameTv;
		TextView addressTv;
		TextView pairBtn;
	}

	public interface OnPairButtonClickListener {
		public abstract void onPairButtonClick(int position);
	}
}
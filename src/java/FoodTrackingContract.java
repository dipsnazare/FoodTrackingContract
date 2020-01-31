/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Deeps
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodTrackingContract implements SmartContract
{

    private String superAdmin;
    private String Admin;

    private final List<String> adminList = new ArrayList<>();
//    private final Map<String, List<String>> itemList = new HashMap<>();

    private final Map<String, GeneratedItem> generatedItemMap = new HashMap<>();
    private final Map<String, TransferredItem> transferredItemMap = new HashMap<>();
    private final Map<String, Manufacturer> manufacturerList = new HashMap<>();
    private final Map<String, Seller> sellerList = new HashMap<>();

    @Override
    public void create(Context context, Object... params)
    {
        this.superAdmin = context.msgSender;
    }

    @Override
    public void update(Context context, String command, Object... params)
    {
        if (command.equalsIgnoreCase("ADDADMIN"))
        {
            String adminAddress = (String) params[0];

            addAdmin(context, adminAddress);
        } else if (command.equalsIgnoreCase("ADDGENERATEDITEM"))
        {
            GeneratedItem gi = new GeneratedItem();
            gi.ownerAddress = (String) params[0];
            gi.item = (String) params[1];
            gi.qrcode = (String) params[2];
            gi.qty = (String) params[3];
            gi.rate = (String) params[4];
            gi.date = (String) params[5];

            addGeneratedItem(context, gi);
        } else if (command.equalsIgnoreCase("ADDTRANSFERREDITEM"))
        {
            TransferredItem ti = new TransferredItem();
            ti.ownerAddress = (String) params[0];
            ti.item = (String) params[1];
            ti.qrcode = (String) params[2];
            ti.qty = (String) params[3];
            ti.rate = (String) params[4];
            ti.date = (String) params[5];
            addTransferredItem(context, ti);
        } else if ("ADDSELLER".equalsIgnoreCase(command))
        {
            Seller seller = new Seller();
            seller.ownerAddress = (String) params[0];
            seller.item = (String) params[1];
            seller.qrcode = (String) params[2];
            seller.qty = (String) params[3];
            seller.rate = (String) params[4];
            seller.date = (String) params[5];

            addSeller(context, seller);
        } else if("ADDMANUFACTURER".equalsIgnoreCase(command))
        {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.ownerAddress = (String) params[0];
            manufacturer.item = (String) params[1];
            manufacturer.qrcode = (String) params[2];
            manufacturer.qty = (String) params[3];
            manufacturer.rate = (String) params[4];
            manufacturer.date = (String) params[5];
            addManufacturer(context, manufacturer);
        }
        else
        {
            throw new RuntimeException("Unknown command :" + command);
        }

    }

    private void addGeneratedItem(Context context, GeneratedItem i)
    {
        if (!i.ownerAddress.equals(context.msgSender))
        {
            throw new RuntimeException("Must be manufacturer of " + i.item + " to add");
        }

        generatedItemMap.put(i.qrcode, i);
    }

    @Override
    public Object query(Context context, String command, Object... params)
    {
        if ("GETGENERATEDITEM".equalsIgnoreCase(command))
        {
            String qrcode = (String) params[0];
            return getGeneratedItem(context, qrcode);
        } else if ("GETTRANSFERREDITEM".equalsIgnoreCase(command))
        {
            String qrcode = (String) params[0];
            return getTransferredItem(context, qrcode);
        } else if ("GETMANUFACTURER".equalsIgnoreCase(command))
        {
            String manufacturerAddress = (String) params[0];
            return getManufacturer(context, manufacturerAddress);
        } else if("GETSELLER".equalsIgnoreCase(command))
        {
            String sellerAddress = (String) params[0];
            return getSeller(context, sellerAddress);
        }

        throw new RuntimeException("Unknown command " + command);

    }

    private Object getGeneratedItem(Context context, String qrcode)
    {
        GeneratedItem i = generatedItemMap.get(qrcode);
        return i;
    }

    private void addTransferredItem(Context context, TransferredItem i)
    {
        if (!i.ownerAddress.equals(context.msgSender))
        {
            throw new RuntimeException("Must be manufacturer of " + i.item + " to add");
        }

        String sender = context.msgSender;
        transferredItemMap.put(i.qrcode, i);
    }

    private Object getTransferredItem(Context context, String qrcode)
    {
        TransferredItem i = transferredItemMap.get(qrcode);
        return i;
    }

    private void addAdmin(Context context, String adminAddress)
    {
        if (!context.msgSender.equals(superAdmin))
        {
            throw new RuntimeException("Must be superadmin to add new manufacturer");
        }

        adminList.add(adminAddress);
    }

//    private Object getAdmin(Context context, String item)
//    {
//        if (!superAdin.equals(context.msgSender))
//        {
//            throw new RuntimeException("Must be super administrator to add admin");
//        }
//        return adminList.get(item);
//    }
    private void addManufacturer(Context context, Manufacturer m)
    {
        if (!context.msgSender.equals(Admin))
        {
            throw new RuntimeException("Must be admin to new Manufacturer");
        }
//        String manufacturerID = (String) params[0];
        manufacturerList.put(m.ownerAddress, m);
    }

    private Object getManufacturer(Context context, String manufacturerAddress)
    {
        Manufacturer manufacturer = manufacturerList.get(manufacturerAddress);
        return manufacturer;
    }

    private void addSeller(Context context, Seller s)
    {
        if (!context.msgSender.equals(Admin))
        {
            throw new RuntimeException("Must be admin to new Seller");
        }

        sellerList.put(s.ownerAddress, s);
    }

    private Object getSeller(Context context, String sellerAddress)
    {
        Seller seller = sellerList.get(sellerAddress);
        return seller;
    }

}

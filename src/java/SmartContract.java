/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Deeps
 */
public interface SmartContract {

    public void create(Context context, Object... params);

    public void update(Context context, String command, Object... params);

    public Object query(Context context, String command, Object... params);

}

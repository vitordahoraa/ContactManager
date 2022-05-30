package com.example.gerenciadorcontatos.database;


import com.example.gerenciadorcontatos.Contatos.Contatos;
import com.example.gerenciadorcontatos.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import java.util.ArrayList;

public class database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gerenciadorContatos";
    private static final String TABLE_CONTATOS = "contatos";
    private static final String TABLE_TIPOTELEFONE = "telefone_tipo";
    private static final String CASA = "CASA";
    private static final String CELULAR = "CELULAR";
    private static final String TRABALHO = "TRABALHO";



    private static final String CREATE_TABLE_CONTATOS = "CREATE TABLE " + TABLE_CONTATOS + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), " +
            "telefone VARCHAR(100), " +
            "id_telefone_tipo INTEGER, " +
            "CONSTRAINT fk_tipo_telefone FOREIGN KEY (id_telefone_tipo) references telefone_tipo(_id))";

    private static final String CREATE_TABLE_TIPO_CONTATOS = "CREATE TABLE " + TABLE_TIPOTELEFONE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100))";

    private static final String INSERT_TIPO_TELEFONE(String tipoTelefone){
        String INSERT = "INSERT INTO " + TABLE_CONTATOS + " (nome)"+
        "VALUES"+
        "(" + tipoTelefone +")";
        return INSERT;
    }
    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.delete(TABLE_CONTATOS,null,null);
        sqLiteDatabase.delete(TABLE_TIPOTELEFONE,null,null);

        sqLiteDatabase.execSQL(CREATE_TABLE_CONTATOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_TIPO_CONTATOS);
        ContentValues cv = new ContentValues();
        cv.put("nome",CELULAR);
        sqLiteDatabase.insert(TABLE_TIPOTELEFONE,null,cv);
        cv.put("nome",CASA);
        sqLiteDatabase.insert(TABLE_TIPOTELEFONE,null,cv);
        cv.put("nome",TRABALHO);
        sqLiteDatabase.insert(TABLE_TIPOTELEFONE,null,cv);
        ContentValues cv2 = new ContentValues();
        sqLiteDatabase.insert(TABLE_CONTATOS,null,cv2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CONTATOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_TIPO_CONTATOS);
        onCreate(sqLiteDatabase);
    }

    public long createContatos (Contatos c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome()  );
        cv.put("telefone",c.getTelefone());
        cv.put("id_telefone_tipo",c.getTipoTelefone());
        long id = db.insert(TABLE_CONTATOS, null, cv);
        db.close();
        return id;
    }

    public long updateContatos (Contatos c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        cv.put("telefone",c.getTelefone());
        cv.put("id_telefone_tipo",c.getTipoTelefone());
        long id = db.update(TABLE_CONTATOS,cv,"_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }


    public long deleteContato(Contatos c){
        SQLiteDatabase db = this.getWritableDatabase();
        long id =db.delete(TABLE_CONTATOS,"_id = ?",new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }

    public Contatos getContatoById(int id){
        Contatos c = new Contatos();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] colunas={"_id","nome","telefone","id_telefone_tipo"};
        Cursor output =db.query(TABLE_CONTATOS,colunas,"_id = ?",new String[]{String.valueOf(id)},null,null,null);
        output.moveToFirst();

        c.setId(output.getInt(0));
        c.setNome(output.getString(1));
        c.setTelefone(output.getString(2));
        c.setTipoTelefone(output.getInt(3));
        output.close();
        db.close();
        return c;
    }

    public void getAllContatos (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT A._id, A.nome contato, A.telefone telefone, B.nome tipo FROM contatos A INNER JOIN telefone_tipo B on A.id_telefone_tipo = B._id ORDER BY A.nome ASC";
        Cursor data2 = db.rawQuery(query,null);
        String[] columns = {"A._id", "contato","telefone","tipo"};
        //Cursor data = db.query(TABLE_CONTATOS + " A INNER JOIN telefone_tipo B on A.id_telefone_tipo = B._id ", columns,null, null, null, null,"A.nome ASC");
        int[] to = {R.id.textViewIdListContato, R.id.textViewNomeListContato, R.id.textViewTelefoneContato,R.id.textViewTipoTelefoneContatoListView};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,R.layout.contato_item_list,data2,columns,to,0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllNameContatos (ArrayList<Integer> listContatosId, ArrayList<String> listContatosName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CONTATOS, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listContatosId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listContatosName.add(data.getString(nameColumnIndex));
        }
        db.close();
        data.close();
    }

    public int getTipoContatoId(String TipoTelefone){

        int id;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colunas={"_id","nome"};
        Cursor output =db.query(TABLE_TIPOTELEFONE,colunas,"nome = ?",new String[]{TipoTelefone},null,null,null);
        output.moveToFirst();
        id = output.getInt(0);
        output.close();
        return id;

    }

    public void getAllTipoTelefone (ArrayList<Integer> listTipoTelefoneID, ArrayList<String> listTipoTelefoneName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_TIPOTELEFONE, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listTipoTelefoneID.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listTipoTelefoneName.add(data.getString(nameColumnIndex));
        }
        db.close();
        data.close();
    }


}



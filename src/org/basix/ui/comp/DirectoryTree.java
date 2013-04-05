package org.basix.ui.comp;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Tree;
import org.basix.common.pattern.ICommand;
import org.basix.util.AppConstant;
import org.basix.util.IconConstant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: abilhakim
 * Date: 4/3/13
 * Time: 11:48 AM
 */
public class DirectoryTree extends Tree implements Tree.ExpandListener, Tree.ValueChangeListener  {


    private ICommand onSelectItem;
    private String path;

    public DirectoryTree(String path){
        super();
        this.path = path;
        addListener((Tree.ExpandListener)this);
        addListener((Tree.ValueChangeListener)this);
        final File sampleDir = new File(path);
        populateNode(sampleDir.getAbsolutePath(), null);
        setImmediate(true);
    }


    public void setOnSelectItem(ICommand onSelectItem) {
        this.onSelectItem = onSelectItem;
    }

    /**
     * A node has been expanded.
     *
     * @param event the Expand event.
     */
    @Override
    public void nodeExpand(ExpandEvent event) {
        final Item i = this.getItem(event.getItemId());
        if (!this.hasChildren(i)) {
            // populate tree's node which was expanded
            populateNode(event.getItemId().toString(), event.getItemId());
        }
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {

       if(onSelectItem!=null){
           Map map = new HashMap();
           map.put("value",this.getValue());
           onSelectItem.execute(map);
       }else{
           System.out.println("Please set onSelectItem command");
       }
    }

    /**
     * refresh tree/reload
     */
    public void refreshTree(){
        final File sampleDir = new File(path);

       removeListener((Tree.ExpandListener) this);
       removeListener((Tree.ValueChangeListener) this);
       removeAllItems();

       addListener((Tree.ExpandListener) this);
       addListener((Tree.ValueChangeListener) this);
       populateNode(sampleDir.getAbsolutePath(), null);
       setImmediate(true);
    }


    private void populateNode(String file, Object parent) {
        final File subdir = new File(file);
        final File[] files = subdir.listFiles();
        for (int x = 0; x < files.length; x++) {
            try {
                // add new item (String) to tree
                final String path = files[x].getCanonicalPath();
                this.addItem(path);

                int idx;

                if(AppConstant.PLATFORM.equals("unix")){
                    idx = path.lastIndexOf("/");
                }else{
                    idx = path.lastIndexOf("\\");
                }

                String lbl ;
                try {
                    lbl = path.substring(idx+1);
                } catch (Exception e) {
                    lbl = path;
                    //
                }
                this.setItemCaption(path, lbl);
                if(files[x].isDirectory()){
                    this.setItemIcon(path, IconConstant.getInstance().getIcon("icon.places.folder"));
                }else{
                    if(path.endsWith(".groovy")) this.setItemIcon(path, IconConstant.getInstance().getIcon("icon.mime.txt"));
                    else if(path.endsWith(".xml") || path.endsWith("html") || path.endsWith(".htm") ) this.setItemIcon(path, IconConstant.getInstance().getIcon("icon.mime.xml"));
                    else this.setItemIcon(path, IconConstant.getInstance().getIcon("icon.places.file"));
                }
                // set parent if this item has one
                if (parent != null) {
                    this.setParent(path, parent);
                }
                // check if item is a directory and read access exists
                if (files[x].isDirectory() && files[x].canRead()) {
                    // yes, childrens therefore exists
                    this.setChildrenAllowed(path, true);
                } else {
                    // no, childrens therefore do not exists
                    this.setChildrenAllowed(path, false);
                }
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}

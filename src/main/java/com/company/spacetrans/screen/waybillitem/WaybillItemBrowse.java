package com.company.spacetrans.screen.waybillitem;

import com.company.spacetrans.entity.WaybillItem;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_WaybillItem.browse")
@UiDescriptor("waybill-item-browse.xml")
@LookupComponent("waybillItemsTable")
public class WaybillItemBrowse extends StandardLookup<WaybillItem> {

}
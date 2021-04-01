package com.company.spacetrans.screen.discounts;

import com.company.spacetrans.entity.Discounts;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Discounts.browse")
@UiDescriptor("discounts-browse.xml")
@LookupComponent("discountsesTable")
public class DiscountsBrowse extends StandardLookup<Discounts> {

}
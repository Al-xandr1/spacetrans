package com.company.spacetrans.screen.discounts;

import com.company.spacetrans.entity.Discounts;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Discounts.edit")
@UiDescriptor("discounts-edit.xml")
@EditedEntityContainer("discountsDc")
public class DiscountsEdit extends StandardEditor<Discounts> {

}
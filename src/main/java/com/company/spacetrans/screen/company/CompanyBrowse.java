package com.company.spacetrans.screen.company;

import com.company.spacetrans.entity.Company;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Company.browse")
@UiDescriptor("company-browse.xml")
@LookupComponent("companiesTable")
public class CompanyBrowse extends StandardLookup<Company> {

}
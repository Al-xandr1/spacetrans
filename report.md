# Training task report

### BUGs:

* __STUDIO__: При проставлении формата в дизайнере он сбрасывается при переключении фокуса с поля Number format. При попытке проставить вручную
  аннотацию @NumberFormat(pattern = "###.00%") - она пропадает после переключения в дизайнер

* __STUDIO__: Нельзя проставить число в поле volume (на котором стоит @NumberFormat(pattern = "###.00%", groupingSeparator = ",", decimalSeparator = "
  .")) - возникает алерт "Must be a double", хотя вводится значение 0.5 или 0,5

* __JMIX__: java.lang.ClassCastException: class com.company.spacetrans.entity.Waybill cannot be cast to class
  org.eclipse.persistence.descriptors.changetracking.ChangeTracker (com.company.spacetrans.entity.Waybill and
  org.eclipse.persistence.descriptors.changetracking.ChangeTracker are in unnamed module of loader 'app'). At
  io.jmix.eclipselink.impl.EntityChangedEventManager.internalCollect(EntityChangedEventManager.java:135). Это возникает потому, что enhancer не
  прописывает интерфейс ChangeTracker к сущности Waybill. Ранее уже регистрировал - https://github.com/Haulmont/jmix-core/issues/125

* __STUDIO__: при генерации EntityPicker'а на композитное поле генерируется поле <code>\<action id="open_composition" type="entity_open_composition"
  \/\></code> а надо то,что написано тут <code>
  io.jmix.ui.action.entitypicker.EntityOpenCompositionAction.ID</code>

* __JMIX__: при проставлении только некоторых ИДшников меню в аннотацию @MenuPolicy(menuIds = {"waybill-accounting", "users"}) пропадают все меню.

* __STUDIO__: Ошибка при создании fetch-plan из визарда. Ранее уже регистрировал https://youtrack.haulmont.com/issue/JST-962



### Suggestions:

* __JMIX__: добавить бы возможность inject'а LOG'а
* __JMIX__: Сейчас т.к. UI контроллеры - это не Spring бины - там самописный inject @Autowared полей. Хотелось бы иметь inject через конструкторы и свойства. Могло бы быть полезно при тестировании с моками без спринг контекста.
* __TRAINING-TASK__: можно прописать в каком пункте использовать (или дополнительно использовать) разные экраны (browser, editor, browser+editor, master detail screen)
* __TRAINING-TASK__: в нужных местах уточнить, что нужно прикручивать функциональность через action'ы, а не привязывать обработчики к кнопкам, чтобы лучше понять сразу разницу action'ов и элементов UI
* __JMIX__: Сейчас обработкой событий об изменении сущностей занимаются spring бины (т.к. могут слушать события org.springframework.context.event.EventListener). UI контроллер этого делать не может. Проблема: реакция UI на изменение сущностей ограничена - только активное окно (контроллер) обновляет UI компоненты, и может получать события от UiEventPublisher.publishEvent(). В последнем случае, если контроллер не активен, то он перестаёт получать UI события. Хотелось бы иметь возможность оповещать контроллеры, для поддержания актуального состояния UI без необходимости перезагружать страницу. А то, как использовать этот механизм - уже на усмотрение прикладного разработчика.

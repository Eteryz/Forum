import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationDlgComponentComponent } from './confirmation-dlg-component.component';

describe('ConfirmationDlgComponentComponent', () => {
  let component: ConfirmationDlgComponentComponent;
  let fixture: ComponentFixture<ConfirmationDlgComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmationDlgComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmationDlgComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

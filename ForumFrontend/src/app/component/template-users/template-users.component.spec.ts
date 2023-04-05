import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateUsersComponent } from './template-users.component';

describe('TemplateUsersComponent', () => {
  let component: TemplateUsersComponent;
  let fixture: ComponentFixture<TemplateUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

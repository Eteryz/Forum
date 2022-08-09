import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateArticlesComponent } from './template-articles.component';

describe('TemplateArticlesComponent', () => {
  let component: TemplateArticlesComponent;
  let fixture: ComponentFixture<TemplateArticlesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateArticlesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateArticlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

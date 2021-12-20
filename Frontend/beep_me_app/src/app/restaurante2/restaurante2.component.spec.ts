import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Restaurante2Component } from './restaurante2.component';

describe('Restaurante2Component', () => {
  let component: Restaurante2Component;
  let fixture: ComponentFixture<Restaurante2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Restaurante2Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Restaurante2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

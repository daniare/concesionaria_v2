import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CocheService } from '../service/coche.service';
import { ICoche, Coche } from '../coche.model';

import { CocheUpdateComponent } from './coche-update.component';

describe('Coche Management Update Component', () => {
  let comp: CocheUpdateComponent;
  let fixture: ComponentFixture<CocheUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cocheService: CocheService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CocheUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CocheUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CocheUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cocheService = TestBed.inject(CocheService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const coche: ICoche = { id: 456 };

      activatedRoute.data = of({ coche });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(coche));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Coche>>();
      const coche = { id: 123 };
      jest.spyOn(cocheService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ coche });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: coche }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(cocheService.update).toHaveBeenCalledWith(coche);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Coche>>();
      const coche = new Coche();
      jest.spyOn(cocheService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ coche });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: coche }));
      saveSubject.complete();

      // THEN
      expect(cocheService.create).toHaveBeenCalledWith(coche);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Coche>>();
      const coche = { id: 123 };
      jest.spyOn(cocheService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ coche });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cocheService.update).toHaveBeenCalledWith(coche);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

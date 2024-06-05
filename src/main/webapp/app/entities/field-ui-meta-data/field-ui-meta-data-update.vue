<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.fieldUIMetaData.home.createOrEditLabel" data-cy="FieldUIMetaDataCreateUpdateHeading">
          Create or edit a Field UI Meta Data
        </h2>
        <div>
          <div class="form-group" v-if="fieldUIMetaData.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fieldUIMetaData.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-label">Label</label>
            <input
              type="text"
              class="form-control"
              name="label"
              id="field-ui-meta-data-label"
              data-cy="label"
              :class="{ valid: !v$.label.$invalid, invalid: v$.label.$invalid }"
              v-model="v$.label.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-inputInterface">Input Interface</label>
            <input
              type="text"
              class="form-control"
              name="inputInterface"
              id="field-ui-meta-data-inputInterface"
              data-cy="inputInterface"
              :class="{ valid: !v$.inputInterface.$invalid, invalid: v$.inputInterface.$invalid }"
              v-model="v$.inputInterface.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-width">Width</label>
            <input
              type="text"
              class="form-control"
              name="width"
              id="field-ui-meta-data-width"
              data-cy="width"
              :class="{ valid: !v$.width.$invalid, invalid: v$.width.$invalid }"
              v-model="v$.width.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-note">Note</label>
            <input
              type="text"
              class="form-control"
              name="note"
              id="field-ui-meta-data-note"
              data-cy="note"
              :class="{ valid: !v$.note.$invalid, invalid: v$.note.$invalid }"
              v-model="v$.note.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-validationPattern">Validation Pattern</label>
            <input
              type="text"
              class="form-control"
              name="validationPattern"
              id="field-ui-meta-data-validationPattern"
              data-cy="validationPattern"
              :class="{ valid: !v$.validationPattern.$invalid, invalid: v$.validationPattern.$invalid }"
              v-model="v$.validationPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-options">Options</label>
            <input
              type="text"
              class="form-control"
              name="options"
              id="field-ui-meta-data-options"
              data-cy="options"
              :class="{ valid: !v$.options.$invalid, invalid: v$.options.$invalid }"
              v-model="v$.options.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-displayOptions">Display Options</label>
            <input
              type="text"
              class="form-control"
              name="displayOptions"
              id="field-ui-meta-data-displayOptions"
              data-cy="displayOptions"
              :class="{ valid: !v$.displayOptions.$invalid, invalid: v$.displayOptions.$invalid }"
              v-model="v$.displayOptions.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-conditions">Conditions</label>
            <input
              type="text"
              class="form-control"
              name="conditions"
              id="field-ui-meta-data-conditions"
              data-cy="conditions"
              :class="{ valid: !v$.conditions.$invalid, invalid: v$.conditions.$invalid }"
              v-model="v$.conditions.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-ui-meta-data-translations">Translations</label>
            <input
              type="text"
              class="form-control"
              name="translations"
              id="field-ui-meta-data-translations"
              data-cy="translations"
              :class="{ valid: !v$.translations.$invalid, invalid: v$.translations.$invalid }"
              v-model="v$.translations.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./field-ui-meta-data-update.component.ts"></script>

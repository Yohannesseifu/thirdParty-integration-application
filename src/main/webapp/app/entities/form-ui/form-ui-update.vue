<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.formUi.home.createOrEditLabel" data-cy="FormUiCreateUpdateHeading">
          Create or edit a Form Ui
        </h2>
        <div>
          <div class="form-group" v-if="formUi.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="formUi.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="form-ui-formName">Form Name</label>
            <input
              type="text"
              class="form-control"
              name="formName"
              id="form-ui-formName"
              data-cy="formName"
              :class="{ valid: !v$.formName.$invalid, invalid: v$.formName.$invalid }"
              v-model="v$.formName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="form-ui-formDescription">Form Description</label>
            <input
              type="text"
              class="form-control"
              name="formDescription"
              id="form-ui-formDescription"
              data-cy="formDescription"
              :class="{ valid: !v$.formDescription.$invalid, invalid: v$.formDescription.$invalid }"
              v-model="v$.formDescription.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="form-ui-formType">Form Type</label>
            <select
              class="form-control"
              name="formType"
              :class="{ valid: !v$.formType.$invalid, invalid: v$.formType.$invalid }"
              v-model="v$.formType.$model"
              id="form-ui-formType"
              data-cy="formType"
            >
              <option v-for="formType in formTypeValues" :key="formType" v-bind:value="formType">{{ formType }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="form-ui-stepOrder">Step Order</label>
            <input
              type="number"
              class="form-control"
              name="stepOrder"
              id="form-ui-stepOrder"
              data-cy="stepOrder"
              :class="{ valid: !v$.stepOrder.$invalid, invalid: v$.stepOrder.$invalid }"
              v-model.number="v$.stepOrder.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="form-ui-thirdPartyIntegration">Third Party Integration</label>
            <select
              class="form-control"
              id="form-ui-thirdPartyIntegration"
              data-cy="thirdPartyIntegration"
              name="thirdPartyIntegration"
              v-model="formUi.thirdPartyIntegration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  formUi.thirdPartyIntegration && thirdPartyIntegrationOption.id === formUi.thirdPartyIntegration.id
                    ? formUi.thirdPartyIntegration
                    : thirdPartyIntegrationOption
                "
                v-for="thirdPartyIntegrationOption in thirdPartyIntegrations"
                :key="thirdPartyIntegrationOption.id"
              >
                {{ thirdPartyIntegrationOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./form-ui-update.component.ts"></script>

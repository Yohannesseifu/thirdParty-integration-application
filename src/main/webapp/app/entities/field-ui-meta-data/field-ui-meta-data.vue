<template>
  <div>
    <h2 id="page-heading" data-cy="FieldUIMetaDataHeading">
      <span id="field-ui-meta-data-heading">Field UI Meta Data</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'FieldUIMetaDataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-field-ui-meta-data"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Field UI Meta Data</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fieldUIMetaData && fieldUIMetaData.length === 0">
      <span>No Field UI Meta Data found</span>
    </div>
    <div class="table-responsive" v-if="fieldUIMetaData && fieldUIMetaData.length > 0">
      <table class="table table-striped" aria-describedby="fieldUIMetaData">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Label</span></th>
            <th scope="row"><span>Input Interface</span></th>
            <th scope="row"><span>Width</span></th>
            <th scope="row"><span>Note</span></th>
            <th scope="row"><span>Validation Pattern</span></th>
            <th scope="row"><span>Options</span></th>
            <th scope="row"><span>Display Options</span></th>
            <th scope="row"><span>Conditions</span></th>
            <th scope="row"><span>Translations</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fieldUIMetaData in fieldUIMetaData" :key="fieldUIMetaData.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FieldUIMetaDataView', params: { fieldUIMetaDataId: fieldUIMetaData.id } }">{{
                fieldUIMetaData.id
              }}</router-link>
            </td>
            <td>{{ fieldUIMetaData.label }}</td>
            <td>{{ fieldUIMetaData.inputInterface }}</td>
            <td>{{ fieldUIMetaData.width }}</td>
            <td>{{ fieldUIMetaData.note }}</td>
            <td>{{ fieldUIMetaData.validationPattern }}</td>
            <td>{{ fieldUIMetaData.options }}</td>
            <td>{{ fieldUIMetaData.displayOptions }}</td>
            <td>{{ fieldUIMetaData.conditions }}</td>
            <td>{{ fieldUIMetaData.translations }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FieldUIMetaDataView', params: { fieldUIMetaDataId: fieldUIMetaData.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FieldUIMetaDataEdit', params: { fieldUIMetaDataId: fieldUIMetaData.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fieldUIMetaData)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="thirdPartyIntegrationApplicationApp.fieldUIMetaData.delete.question" data-cy="fieldUIMetaDataDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-fieldUIMetaData-heading">Are you sure you want to delete Field UI Meta Data {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-fieldUIMetaData"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeFieldUIMetaData()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./field-ui-meta-data.component.ts"></script>
